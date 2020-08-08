package com.zdltech.javaexercise.design;


import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 双重加锁机制
 *
 * 线程安全、延迟加载、效率较高
 */
public class SingLetonDemo03 {
    // 创建内部静态变量
    private static SingLetonDemo03 instance;
    private static Lock lock = new ReentrantLock();
    private SingLetonDemo03(){}

    public static SingLetonDemo03 getInstance(){
        //先判断是否存在，不存在进行加锁处理
        if (instance ==  null){
            // 在同一时刻加了锁的那部分程序只有一个线程可以进入
            lock.lock();//上锁
            try {  //处理业务逻辑
                System.out.println("加锁");
                System.out.println("执行业务逻辑");
                if (instance == null){
                    instance = new SingLetonDemo03();
                }

            }finally {
                System.out.println("释放锁");
                lock.unlock();//释放锁
            }
        }
        return instance;
    }

}
