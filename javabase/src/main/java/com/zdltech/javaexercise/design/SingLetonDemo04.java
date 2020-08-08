package com.zdltech.javaexercise.design;

/**
 * 双重加锁机制
 *
 * 线程安全、延迟加载、效率较高
 */
public class SingLetonDemo04 {
    // 创建内部静态变量
    private static  SingLetonDemo04 instance;
    // 程序运行时创建一个静态只读的进程辅助对象
    private static Object syncRoot = new Object();
    private SingLetonDemo04(){}

    public static SingLetonDemo04 getInstance(){
        //先判断是否存在，不存在进行加锁处理
        if (instance ==  null){
            // 在同一时刻加了锁的那部分程序只有一个线程可以进入
            synchronized (SingLetonDemo04.class){
                //处理业务逻辑
                System.out.println("加锁");
                System.out.println("执行业务逻辑");
                if (instance == null){
                    instance = new SingLetonDemo04();
                }
            }
        }
        return instance;
    }

}
