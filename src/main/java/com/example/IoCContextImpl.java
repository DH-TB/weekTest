package com.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IoCContextImpl implements IoCContext {
    private boolean isGetBean;
    private final List<Class> classList = new ArrayList<>();

    @Override
    public void registerBean(Class<?> beanClazz) {
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
        }

        if (isGetBean) {
            throw new IllegalStateException();
        }

        if (!classList.contains(beanClazz)) {
            classList.add(beanClazz);
        }
    }

    @Override
    public <T> T getBean(Class<T> resolveClazz) throws IllegalAccessException, InstantiationException {
        isGetBean = true;

        if (resolveClazz == null) {
            throw new IllegalArgumentException();
        }
        if (!classList.contains(resolveClazz)) {
            throw new IllegalStateException();
        }

        try {
            resolveClazz.newInstance();
        } catch (IllegalStateException | IllegalAccessException | InstantiationException e) {
            throw e;
        }

        return resolveClazz.newInstance();
    }

    public void getMyBean() throws InstantiationException, IllegalAccessException {
        IoCContextImpl context = new IoCContextImpl();
        context.registerBean(MyBean.class);
        MyBean myBeanInstance = context.getBean(MyBean.class);
    }
}
