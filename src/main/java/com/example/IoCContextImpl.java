package com.example;

import com.example.dependency.CreateOnTheFly;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Stream;

public class IoCContextImpl implements IoCContext {
    private boolean isGetBean;
    private final Map<Class, Class> map = new HashMap<>();
    private Stack<Exception> exceptionStack = new Stack<>();
    private Stack<AutoCloseable> closeStack = new Stack<>();
    public static List<String> countCloseList = new LinkedList<>();

    @Override
    public <T> void registerBean(Class<? super T> resolveClazz, Class<T> beanClazz) throws Exception {
        if (beanClazz == null || resolveClazz == null) {
            throw new IllegalArgumentException("beanClazz or resolveClazz is mandatory");
        }

        judgeNoDefaultConstructor(beanClazz);
        judgeNotInstantiated(beanClazz);
        judgeIllegalGetBean();

        saveBeanMap(resolveClazz, beanClazz);
    }

    private <T> void saveBeanMap(Class<? super T> resolveClazz, Class<T> beanClazz) {
        if (map.containsKey(resolveClazz)) {
            Class aClass = map.get(resolveClazz);
            if (!aClass.equals(beanClazz)) {
                map.put(resolveClazz, beanClazz);
            }
        } else {
            map.put(resolveClazz, beanClazz);
        }
    }

    @Override
    public void registerBean(Class<?> beanClazz) throws Exception {
        if (beanClazz == null) {
            throw new IllegalArgumentException("beanClazz is mandatory");
        }

        judgeNoDefaultConstructor(beanClazz);
        judgeNotInstantiated(beanClazz);
        judgeIllegalGetBean();

        if (!map.containsKey(beanClazz)) {
            map.put(beanClazz, beanClazz);
        }

        Class<?>[] interfaces = beanClazz.getInterfaces();

        for (Class myInterface : interfaces) {
            if (AutoCloseable.class.equals(myInterface)) {
                closeStack.add((AutoCloseable) beanClazz.newInstance());
            }
        }
    }

    @Override
    public <T> T getBean(Class<T> resolveClazz) throws IllegalAccessException, InstantiationException {
        isGetBean = true;

        if (resolveClazz == null) {
            throw new IllegalArgumentException("resolveClazz is null");
        }

        judgeBeanNotRegistered(resolveClazz);
        getSuperClass(resolveClazz);

        T instance = getInstance(resolveClazz);

        return instance;
    }


    private <T> void getSuperClass(Class<T> resolveClazz) {
        List<Class> superClassList = new ArrayList<>();
        getSuperClassList(resolveClazz, superClassList);
        judgeSuperClassList(superClassList);
    }

    private void judgeSuperClassList(List<Class> superClassList) {
        for (Object aClazz : superClassList) {
            if (!map.containsKey(aClazz) && !map.containsValue(aClazz)) {
                throw new IllegalStateException("has super class not be registered");
            }
        }
    }

    private <T> T getInstance(Class<T> clazz) throws IllegalAccessException, InstantiationException {
        T instance;
        if (map.get(clazz) != null) {
            instance = (T) map.get(clazz).newInstance();
        } else {
            instance = clazz.newInstance();
        }
        judgeDependence(clazz, instance);

        return instance;
    }

    private <T> void judgeDependence(Class<T> clazz, T instance) throws IllegalAccessException, InstantiationException {
        Class<? super T> superclass = clazz.getSuperclass();
        Stream<Field> superStream;
        superStream = superclass != null ? Arrays.stream(clazz.getSuperclass().getDeclaredFields()) : Stream.empty();

        Stream<Field> stream = Arrays.stream(clazz.getDeclaredFields());
        Field[] fields = Stream.concat(superStream, stream).filter(field -> field.getAnnotation(CreateOnTheFly.class) != null).toArray(Field[]::new);

        for (Field field : fields) {
            field.setAccessible(true);
            Class<?> aClass = field.getType();

            if (!map.containsKey(aClass)) {
                throw new IllegalStateException("has dependency bean not be registered");
            }
            field.set(instance, aClass.newInstance());
        }
    }

    private <T> void getSuperClassList(Class<T> clazz, List superClassList) {
        Class<? super T> superclass = clazz.getSuperclass();
        if (superclass != null && superclass != Object.class) {
            superClassList.add(superclass);
            getSuperClassList(superclass, superClassList);
        }
    }


    private <T> void judgeBeanNotRegistered(Class<T> resolveClazz) {
        if (!map.containsKey(resolveClazz) && !map.containsValue(resolveClazz)) {
            throw new IllegalStateException("resolveClazz not registered");
        }
    }

    private void judgeIllegalGetBean() {
        if (isGetBean) {
            throw new IllegalStateException("not register bean after get bean");
        }
    }

    private <T> void judgeNotInstantiated(Class<T> beanClazz) throws Exception {
        try {
            beanClazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new IllegalArgumentException("ClassNotInstantiated is abstract");
        } catch (Exception e) {
            if (e.getCause() != null) {
                throw new Exception(e.getMessage(), e.getCause());
            }
        }
    }

    private <T> void judgeNoDefaultConstructor(Class<T> beanClazz) {
        long count = Arrays.stream(beanClazz.getConstructors()).filter(constructor -> constructor.getParameterCount() == 0).count();
        if (count == 0) {
            throw new IllegalArgumentException("ClassNotHaveDefaultConstructor has no default constructor");
        }
    }

    @Override
    public void close() throws Exception {
        while (!closeStack.empty()) {
            AutoCloseable closeable = closeStack.pop();
            try {
                closeable.close();
            } catch (Exception e) {
                exceptionStack.push(e);
            }
        }

        if (!exceptionStack.empty()) {
            throw exceptionStack.pop();
        }
    }

}
