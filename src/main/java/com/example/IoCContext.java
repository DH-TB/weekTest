package com.example;

public interface IoCContext {

    <T> T getBean(Class<T> resolveClazz) throws Exception;

    <T> void registerBean(Class<? super T> resolveClazz, Class<T> beanClazz) throws Exception;
}
