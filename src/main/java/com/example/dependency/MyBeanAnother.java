package com.example.dependency;

public class MyBeanAnother {

    @CreateOnTheFly
    private MyDependency dependency;

    @CreateOnTheFly
    private MyDependencyAnother dependencyAnother;

}
