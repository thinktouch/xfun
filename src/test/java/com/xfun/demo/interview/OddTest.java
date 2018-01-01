package com.xfun.demo.interview;

/**
 * Created by xfun on 5/23/17.
 */
public class OddTest {

    public static void main(String[] args){
        Integer a = 100;
        Integer b = 100;
        Integer c = 200;
        Integer d = 200;
        System.out.println(a == b);
        System.out.println(c == d);

        Integer e = 2147483647;
        Integer f = e + 1;
        System.out.println(e > f);


        String s1 = "a";
        String s2 = "a";
        String s3 = new String("a");
        String s4 = new String("a");

        System.out.println(s1 == s2);
        System.out.println(s1 == s3);
        System.out.println(s3 == s4);


        String s5 = "ab";
        String s6 = "a" + "b";
        String s7 = new String("a") + new String("b");
        String s8 = new String("ab");
        System.out.println(s5 == s6);
        System.out.println(s5 == s7);
        System.out.println(s7 == s8);
    }

}
