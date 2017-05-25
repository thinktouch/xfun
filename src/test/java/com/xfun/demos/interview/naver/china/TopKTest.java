package com.xfun.demos.interview.naver.china;

import com.xfun.utils.FileUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static com.xfun.utils.FileUtil.readFileByLine;

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
        List<Object> content = new ArrayList<Object>();
        for (int i = 0; i <1000000 ; i++) {
            int randomData = random.nextInt(10000);
            content.add(randomData);
        }

        FileUtil.writeFileByLine(content, filePath, fileName);
        System.out.println("write file finished! filePath:" + filePath + fileName);
    }

    public static void readData(){

        long startTime = System.currentTimeMillis();
        String filePath = TopKTest.class.getResource("/").toString().concat("data/").concat("1000k.txt").replace("file:", "");
        System.out.println(filePath);
        String content = FileUtil.readFileByLine(filePath);
        System.out.println("readData total(" + content.split(",").length + ") cost time (ms): " + (System.currentTimeMillis() - startTime));

    }

}
