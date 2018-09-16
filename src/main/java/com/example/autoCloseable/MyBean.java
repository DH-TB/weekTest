package com.example.autoCloseable;

import com.example.IoCContextImpl;

public class MyBean implements AutoCloseable {
    @Override
    public void close() {
        System.out.println(2);

        IoCContextImpl.countCloseList.add("MyBean");
    }
}
