package com.example.dependency;

public class MyBean {

    @CreateOnTheFly
    private MyDependency dependency;

    public String getString(){
        return "my bean string dependence";
    }

    public String getDependency(){
        return dependency.getString();
    }
}
