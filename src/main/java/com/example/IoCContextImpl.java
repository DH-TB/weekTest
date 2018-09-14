package com.example;

import com.example.otherClass.MyBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IoCContextImpl implements IoCContext {
    private boolean isGetBean;
    private final List<Class> classList = new ArrayList<>();

    @Override
    public void registerBean(Class<?> beanClazz) throws Exception {
        if (beanClazz == null) {
            throw new IllegalArgumentException("beanClazz is mandatory");
        }

        long count = Arrays.stream(beanClazz.getConstructors()).filter(constructor -> constructor.getParameterCount() == 0).count();
        if (count == 0) {
            throw new IllegalArgumentException("$bean ClassNotHaveDefaultConstructor has no default constructor");
        }

        try {
            beanClazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new IllegalArgumentException("$bean ClassNotInstanctiated is abstract");
        } catch (Exception e) {
            if (e.getCause() != null) {
                throw new Exception(e.getCause());
            }
        }

        if (isGetBean) {
            throw new IllegalStateException();
        }

        if (!classList.contains(beanClazz)) {
            classList.add(beanClazz);
        }
    }

    @Override
    public <T> T getBean(Class<T> resolveClazz) throws Exception {
        isGetBean = true;

        if (resolveClazz == null) {
            throw new IllegalArgumentException();
        }
        if (!classList.contains(resolveClazz)) {
            throw new IllegalStateException();
        }
        T instance = resolveClazz.newInstance();

        return instance;
    }

    public void getMyBean() throws Exception {
        IoCContextImpl context = new IoCContextImpl();
        context.registerBean(MyBean.class);
        MyBean myBeanInstance = context.getBean(MyBean.class);
    }
}
