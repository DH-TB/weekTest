package com.example.interfaceClass;

public class MyBeanImpl extends MyBeanBaseClass implements MyBeanBase, MyBeanBaseAnother {

    @Override
    public String getString() {
        return "interface bean can work";
    }
}
