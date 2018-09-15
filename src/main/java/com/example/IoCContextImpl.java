package com.example;

import com.example.dependency.CreateOnTheFly;
import com.example.dependency.MyDependency;

import java.lang.reflect.Field;
import java.util.*;

public class IoCContextImpl implements IoCContext {
    private boolean isGetBean;
    private final Map<Class, Class> map = new HashMap<>();

    @Override
    public <T> void registerBean(Class<? super T> resolveClazz, Class<T> beanClazz) throws Exception {
        if (beanClazz == null || resolveClazz == null) {
            throw new IllegalArgumentException("beanClazz or resolveClazz is mandatory");
        }

        judgeNoDefaultConstructor(beanClazz);

        judgeNotInstantiated(beanClazz);

        judgeIllegalGetBean();

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

    }

    @Override
    public <T> T getBean(Class<T> resolveClazz) throws IllegalAccessException, InstantiationException {
        isGetBean = true;

        if (resolveClazz == null) {
            throw new IllegalArgumentException("resolveClazz is null");
        }

        judgeBeanNotRegistered(resolveClazz);
        T instance;
        if(map.get(resolveClazz) != null){
            instance = (T) map.get(resolveClazz).newInstance();
        }
        else {
            instance = resolveClazz.newInstance();
        }

        judgeDependenceBean(resolveClazz, instance);

        return instance;
    }
    <T> void judgeDependenceBean(Class<T> clazz, T instance) throws IllegalAccessException, InstantiationException {
        Field[] fields = Arrays.stream(clazz.getDeclaredFields()).filter(field -> field.getAnnotation(CreateOnTheFly.class) != null).toArray(Field[]::new);
        for(Field field : fields){
            field.setAccessible(true);
            Class<?> aClass = field.getType();

            if(!map.containsKey(aClass)){
                throw new IllegalStateException();
            }
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
}
