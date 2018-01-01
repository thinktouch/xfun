package com.xfun.demo.interview;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by xfun on 4/16/17.
 */
public class SchedulerTest {

    public static void main(String[] args){

        final ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(4);

        final Runnable task = new Runnable() {
            public void run() {
                System.out.println("love xiaoyu!!  -- " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            }
        };

        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        scheduledExecutorService.scheduleAtFixedRate(task, 1, 3, TimeUnit.SECONDS);


    }

}
