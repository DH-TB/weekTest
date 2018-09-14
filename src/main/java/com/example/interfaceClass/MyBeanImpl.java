package com.example.interfaceClass;

public class MyBeanImpl implements MyBeanBase, MyBeanBaseAnother {

    @Override
    public String getString() {
        return "interface bean can work";
    }
}
