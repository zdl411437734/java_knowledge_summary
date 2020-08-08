package com.zdltech.javaexercise.javabase.annotation;



public @interface MyAnnotation {
    //key
    public String key() default "key";

    //value
    public String value() default "MyAnnotation";

    //array
    public String[] url();
}
