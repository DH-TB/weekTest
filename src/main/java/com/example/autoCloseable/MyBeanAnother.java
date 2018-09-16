package com.example.autoCloseable;

public class MyBeanAnother implements AutoCloseable {
    private boolean isClosed;

    @Override
    public void close() {
        this.isClosed = true;
    }

    public boolean isClosed() {
        return isClosed;
    }
}
