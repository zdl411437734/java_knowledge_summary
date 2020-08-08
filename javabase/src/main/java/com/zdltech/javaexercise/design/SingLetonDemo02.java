package com.zdltech.javaexercise.design;

/**
 * 懒汉模式-只适用于单线程
 * 如果想线程安全 需要设置 synchronized  性能损失
 */
public class SingLetonDemo02 {
    //构建指向自己实例的私有静态引用
    private static SingLetonDemo02 singLetonDemo02;
    //私有构造方法
    private SingLetonDemo02(){
    }

    //以自己实例为返回值的静态的公有方法，静态工厂方法


    public static SingLetonDemo02 getSingLetonDemo02() {
        //被动创建，真正需要使用时才去创建
        if (singLetonDemo02==null){
            singLetonDemo02 = new SingLetonDemo02();
        }
        return singLetonDemo02;
    }
}
