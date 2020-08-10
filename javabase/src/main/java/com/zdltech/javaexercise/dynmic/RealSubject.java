package com.zdltech.javaexercise.dynmic;


public class RealSubject implements Suject {
    @Override
    public void rent() {
        System.out.println("RealSubject rent is run!");
    }

    @Override
    public void hello(String str) {
        System.out.println("RealSubject hello is Run!");
    }
}
