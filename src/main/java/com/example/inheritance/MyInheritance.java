package com.example.inheritance;

import com.example.dependency.CreateOnTheFly;

public class MyInheritance extends MyInheritSuperClass{

    @CreateOnTheFly
    private MyClass myClass;
}
