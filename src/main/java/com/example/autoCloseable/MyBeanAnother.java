package com.example.autoCloseable;

import com.example.IoCContextImpl;

public class MyBeanAnother implements AutoCloseable {

    @Override
    public void close() {
        System.out.println(1);
        IoCContextImpl.countCloseList.add("MyBeanAnother");
    }
}
