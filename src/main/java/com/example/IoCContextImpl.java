package com.example;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IoCContextImpl implements IoCContext{

    private List<Class> beanList = new ArrayList<>();

    @Override
    public void registerBean(Class<?> beanClazz) throws IllegalAccessException, InstantiationException {
        if(beanClazz == null){
            throw new IllegalArgumentException("beanClazz is mandatory");
        }

        long count = Arrays.stream(beanClazz.getConstructors()).filter(constructor -> constructor.getParameterCount() == 0).count();
        if(count == 0){
            throw new IllegalArgumentException("$bean ClassNotHaveDefaultConstructor has no default constructor");
        }

        try {
            beanClazz.newInstance();
        }catch (InstantiationException | IllegalAccessException e){
            throw new IllegalArgumentException("$bean ClassNotInstance is abstract");
        }

        beanList.add(beanClazz);
    }

    @Override
    public <T> T getBean(Class<T> resolveClazz) {
        return null;
    }

    public void getMyBean() throws InstantiationException, IllegalAccessException {
        IoCContextImpl context = new IoCContextImpl();
        context.registerBean(MyBean.class);
        MyBean myBeanInstance = context.getBean(MyBean.class);
    }
}
