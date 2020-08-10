package com.zdltech.javaexercise.dynmic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class DynmicSubjectProxy implements InvocationHandler {
    //代理的真实对象
    private Object subject;
    //构造方法，给我们要代理的真实对象赋值
    public DynmicSubjectProxy(Object subject){
        this.subject = subject;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //在代理真实对象前我们可以添加一些自己的操作
        System.out.println("Proxy before is run");
        System.out.println("Method:"+method);
        //当代理对象调用真实对象的方法时，其会自动的跳转到代理对象关联的Handler对象的invoke方法进行调用
        Object result = method.invoke(subject,args);
        //在代理真实对象后我们也可以添加一些自己的操作
        System.out.println("Proxy after is Run");
        return result;
    }
}
