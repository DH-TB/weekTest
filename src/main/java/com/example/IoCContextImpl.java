package com.example;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IoCContextImpl implements IoCContext{

    private List<Class> beanList = new ArrayList<>();

    @Override
    public void registerBean(Class<?> beanClazz) {
        if(beanClazz == null){
            throw new IllegalArgumentException("beanClazz is mandatory");
        }


        long count = Arrays.stream(beanClazz.getConstructors()).filter(constructor -> constructor.getParameterCount() == 0).count();
        if(count == 0){
            throw new IllegalArgumentException("$bean full name$ has no default constructor");
        }

        beanList.add(beanClazz);
    }

    @Override
    public <T> T getBean(Class<T> resolveClazz) {
        return null;
    }

    public void getMyBean(){
        IoCContextImpl context = new IoCContextImpl();
        context.registerBean(MyBean.class);
        MyBean myBeanInstance = context.getBean(MyBean.class);
    }
}
