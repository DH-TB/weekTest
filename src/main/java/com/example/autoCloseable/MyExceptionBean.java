package com.example.autoCloseable;

public class MyExceptionBean implements AutoCloseable {
    @Override
    public void close() throws Exception {
        throw new IllegalAccessException();
    }
}
