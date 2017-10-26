package com.xfun.demos.interview.naver.china;

import com.xfun.demos.interview.SortTest;
import com.xfun.utils.FileUtil;

import java.util.*;

import static org.apache.hadoop.yarn.webapp.hamlet.HamletSpec.LinkType.contents;

/**
 * Created by xfun on 5/25/17.
 */
public class TopKTest {

    public static void main(String[] args){
        //randomData();
        readData();

    }

    public static void randomData(){
        String filePath = TopKTest.class.getResource("/").toString().concat("data/").replace("file:", "");
        System.out.println(filePath);
        String fileName = "1000k.txt";
        Random random = new Random();
        List<Integer> content = new ArrayList<Integer>();
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for (int i = 0; i <1000000 ; ) {
            int randomData = random.nextInt(500000000);
            if(map.get(randomData) != null){
                System.out.println("contains i:" + i + " randomData:" + randomData);
                continue;
            }else{
                map.put(randomData, randomData);
                i++;
            }
        }

        for(Integer i : map.keySet()){
            content.add(i);
        }

        FileUtil.writeFileByLine(content, filePath, fileName);
        System.out.println("write file finished! filePath:" + filePath + fileName);
    }

    public static void readData(){

        long startTime = System.currentTimeMillis();
        String filePath = TopKTest.class.getResource("/").toString().concat("data/").concat("10000k.txt").replace("file:", "");
        System.out.println(filePath);
        String content = FileUtil.readFileByLine(filePath);
        System.out.println("readData total(" + content.split(",").length + ") cost time (ms): " + (System.currentTimeMillis() - startTime));


        startTime = System.currentTimeMillis();
        String[] contents = content.split(",");
        int[] contents_swap = new int[contents.length];
        for(int i = 0; i < contents.length; i++){
            contents_swap[i] = Integer.parseInt(contents[i]);
        }
        Arrays.sort(contents_swap);
        for(int i = contents_swap.length - 1; i > contents_swap.length - 10; i--){
            System.out.print(contents[i] + ",");
        }
        System.out.println("\ntop 10 cost time(ms): " + (System.currentTimeMillis() - startTime));


    }

}
