package com.example;

import com.example.otherClass.MyBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IoCContextImpl implements IoCContext {
    private boolean isGetBean;
    private final List<Class> classList = new ArrayList<>();

    @Override
    public <T> void registerBean(Class<? super T> resolveClazz, Class<T> beanClazz) throws Exception {
        if (beanClazz == null) {
            throw new IllegalArgumentException("beanClazz is mandatory");
        }

        judgeNoDefaultConstructor(beanClazz);

        judgeNotInstantiated(beanClazz);

        judgeIllegalGetBean();

        if (!classList.contains(beanClazz)) {
            classList.add(beanClazz);
        }
    }

    @Override
    public <T> T getBean(Class<T> resolveClazz) throws Exception {
        isGetBean = true;

        if (resolveClazz == null) {
            throw new IllegalArgumentException("resolveClazz is null");
        }

        judgeBeanNotRegistered(resolveClazz);

        T instance = resolveClazz.newInstance();

        return instance;
    }

    private <T> void judgeBeanNotRegistered(Class<T> resolveClazz) {
        if (!classList.contains(resolveClazz)) {
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
            throw new IllegalArgumentException("ClassNotInstanctiated is abstract");
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

    public void getMyBean() throws Exception {
        IoCContextImpl context = new IoCContextImpl();
        context.registerBean(null, MyBean.class);
        MyBean myBeanInstance = context.getBean(MyBean.class);
    }
}
