package com.xfun.utils;

import java.io.*;
import java.util.List;

import static org.apache.hadoop.yarn.webapp.hamlet.HamletSpec.InputType.file;

/**
 * Created by xfun on 5/25/17.
 */
public class FileUtil {

    /**
     * 以行为单位读取文件内容
     *
     * @param filePath
     */
    public static String readFileByLine(String filePath) {
        StringBuilder sb = new StringBuilder();
        File file = new File(filePath);
        BufferedReader bufReader = null;
        try {
            bufReader = new BufferedReader(new FileReader(file));
            String temp;
            while ((temp = bufReader.readLine()) != null) {
                sb.append(temp).append(",");
            }
        } catch (Exception e) {
            e.getStackTrace();
        } finally {
            if (bufReader != null) {
                try {
                    bufReader.close();
                } catch (IOException e) {
                    e.getStackTrace();
                }
            }
        }
        return sb.toString();
    }

    /**
     * 以行为单位写入文件内容
     *
     * @param filePath
     */
    public static void writeFileByLine(List<Integer> content, String filePath, String fileName) {
        BufferedWriter bufWriter = null;
        try {
            File path = new File(filePath);
            if (!path.exists()) {
                path.mkdirs();
            }
            File file = new File(filePath, fileName);
            if(!file.exists()) {
                file.createNewFile();
            }
            bufWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
            for(int temp : content){
                bufWriter.write(temp + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bufWriter != null) {
                try {
                    bufWriter.close();
                } catch (IOException e) {
                    e.getStackTrace();
                }
            }
        }
    }

}
