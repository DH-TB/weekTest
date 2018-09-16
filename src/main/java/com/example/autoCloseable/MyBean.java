package com.example.autoCloseable;

import com.example.IoCContextImpl;

public class MyBean implements AutoCloseable {

    @Override
    public void close() {
        IoCContextImpl.countCloseList.add("MyBean");
    }
}
