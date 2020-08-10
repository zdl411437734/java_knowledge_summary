package com.zdltech.javaexercise.dynmic;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * https://blog.csdn.net/Dream_Weave/article/details/84183247
 */
public class DynmicSubjctTest {
    //测试main入口
    public static void main(String[] args){
        //我们要代理的真实对象
        Suject realSubject = new RealSubject();
        //我们要代理哪个真实对象，就将该对象传进去，最后是通过该真实对象来调用其方法的
        InvocationHandler handler = new DynmicSubjectProxy(realSubject);
        /**
         * 通过Proxy的NewProxyInstance方法来创建我们的代理对象，我们来看看其三个参数
         * 第一个参数handler.getClass().getClassLoader(),我们 这里使用handler这个类的classloader对象 来加载我们的代理对象
         * 第二个参数realSubject.getClass().getInterfaces()，我们这里为代理对象提供的接口是真实对象锁实行的接口，表示我们要代理的该真实对象，这样我就能调用这组接口的方法了
         * 第三个参考handler，我们这里将这个代理对象关联到了上方的InvocationHandler这个对象上
         */
        Suject suject = (Suject) Proxy.newProxyInstance(handler.getClass().getClassLoader(),realSubject.getClass().getInterfaces(),handler);
        System.out.println(suject.getClass().getName());
        suject.rent();
        suject.hello(" world");


    }
}
