package com.zdltech.javaexercise.velocity;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * Velocity的模板引擎使用
 * https://segmentfault.com/a/1190000018862687
 * https://developer.ibm.com/zh/articles/j-lo-velocity1/
 */
public class VelocityDemo {
    public static void main(String[] args) {
        test1();
    }

    public static void test1(){
        Properties properties = new Properties();
        properties.put(VelocityEngine.INPUT_ENCODING, "UTF-8");
        properties.setProperty(VelocityEngine.RESOURCE_LOADER, "classpath");
        properties.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        VelocityEngine engine = new VelocityEngine(properties);
        engine.init();

        Template template = engine.getTemplate("HelloVelocity.vm");
        VelocityContext ctx = new VelocityContext();
        // 域对象加入参数值
        ctx.put("name", "Tsecheng");
        ctx.put("date", (new Date()).toString());
        // list集合
        List temp = new ArrayList();
        temp.add("1");
        temp.add("2");
        ctx.put("list", temp);
        StringWriter sw = new StringWriter();
        template.merge(ctx, sw);
        System.out.println(sw.toString());
    }
}
