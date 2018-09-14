package com.example.otherClass;

public class ClassConstructorThrowException {

    public ClassConstructorThrowException() throws CloneNotSupportedException {
        throw new CloneNotSupportedException("something happened");
    }
}