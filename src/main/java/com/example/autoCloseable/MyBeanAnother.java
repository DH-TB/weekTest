package com.example.autoCloseable;

import com.example.IoCContextImpl;

public class MyBeanAnother implements AutoCloseable {

    @Override
    public void close() {
        IoCContextImpl.countCloseList.add("MyBeanAnother");
    }
}
