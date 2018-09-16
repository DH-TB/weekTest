package com.example.autoCloseable;

public class MyExceptionBeanAnother implements AutoCloseable {
    @Override
    public void close() throws Exception {
        throw new IllegalStateException();
    }
}
