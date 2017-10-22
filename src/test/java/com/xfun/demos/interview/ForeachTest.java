package com.xfun.demos.interview;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xfun on 10/22/17.
 */
public class ForeachTest {
    public static void main(String[] args) {
        List<String> list = new ArrayList<String>();
        list.add("1");
        list.add("2");
        for (String item : list) {
            if ("2".equals(item)) {
                list.remove(item);
            }
        }
        System.out.println(list);
    }
}
