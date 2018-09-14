package com.example;

public interface IoCContext {

    void registerBean(Class<?> beanClazz) throws Exception;

    <T> T getBean(Class<T> resolveClazz) throws Exception;
}
