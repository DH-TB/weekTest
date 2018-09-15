package com.example;

public interface IoCContext {

    void registerBean(Class<?> beanClazz) throws Exception;

    <T> T getBean(Class<T> resolveClazz) throws Exception;

    <T> void registerBean(Class<? super T> resolveClazz, Class<T> beanClazz) throws Exception;
}
