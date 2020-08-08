package com.zdltech.javaexercise.design;

/**
 * 饿汉模式
 */
public class SingLetonDemo01 {
    private static SingLetonDemo01 ourInstance = new SingLetonDemo01();

    public static SingLetonDemo01 getInstance() {
        return ourInstance;
    }

    private SingLetonDemo01() {
    }
}
