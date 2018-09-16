package com.example.inheritance;

import com.example.dependency.CreateOnTheFly;

public class MyInheritSuperClass {

    @CreateOnTheFly
    private MyClass myClass;

    public String getString(){
        return "inherit class string";
    }
}
