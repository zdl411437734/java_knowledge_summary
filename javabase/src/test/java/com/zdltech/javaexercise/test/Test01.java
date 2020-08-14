package com.zdltech.javaexercise.test;

import java.util.ArrayList;
import java.util.List;

public class Test01 {
    public static void main(String[] args) {
        List<Demo> list1 = new ArrayList<>();
        List<Demo> list2 = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            Demo item1 = new Demo();
            item1.setA(25);
            list1.add(item1);
            Demo item2 = new Demo();
            item2.setB(25);
            list2.add(item2);
        }

        for (int i = 0; i < 100000; i++) {
           Demo  item1 = list1.get(i);
           Demo  item2 = list2.get(i);
            if (item1.getA()==item2.getB()){

            }else{
                System.out.println("a!=b==>"+i);
            }
        }
    }

    public static class Demo{
        private Integer a;
        private Integer b;

        public Integer getA() {
            return a;
        }

        public void setA(Integer a) {
            this.a = a;
        }

        public Integer getB() {
            return b;
        }

        public void setB(Integer b) {
            this.b = b;
        }
    }
}
