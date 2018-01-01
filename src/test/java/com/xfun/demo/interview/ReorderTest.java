package com.xfun.demo.interview;

/**
 * Created by xfun on 5/23/17.
 */
public class ReorderTest {

    public static void main(String[] args){
        stringBuilderMethod();
        arrayMethod();
    }

    public static void stringBuilderMethod(){
        String strBefore = "1-2-3-4-5-6--A-B-C-D";
        System.out.println("<stringBuilderMethod>  reorder before: " + strBefore);

        String strAfter = new StringBuilder(strBefore).reverse().toString();
        System.out.println("<stringBuilderMethod>  reorder after:  " + strAfter);
    }

    public static void arrayMethod(){
        String strBefore = "1-2-3-4-5-6--A-B-C-D";
        System.out.println("<arrayMethod>  reorder before: " + strBefore);

        StringBuilder stringBuilder = new StringBuilder();
        for(int i = strBefore.length() - 1; i >= 0; i--){
            stringBuilder.append(strBefore.charAt(i));
        }
        String strAfter = stringBuilder.toString();
        System.out.println("<arrayMethod>  reorder after:  " + strAfter);
    }

}
