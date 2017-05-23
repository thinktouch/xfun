package com.xfun.demos.interview;

import java.util.concurrent.CountDownLatch;

/**
 * Created by xfun on 5/16/17.
 */
public class LatchTest {
    public static void main(String[] args) throws InterruptedException {

        final CountDownLatch latch = new CountDownLatch(5);

        Runnable job = new Runnable() {
            public void run() {
                try {
//                    System.out.println(Thread.currentThread().getName() + " - start");
                    Thread.sleep(3000);
                    System.out.println(Thread.currentThread().getName() + " - end");
                    latch.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };

        Thread t1 = new Thread(job);
        t1.setName("t1");

        Thread t2 = new Thread(job);
        t2.setName("t2");

        Thread t3 = new Thread(job);
        t3.setName("t3");

        Thread t4 = new Thread(job);
        t4.setName("t4");

        Thread t5 = new Thread(job);
        t5.setName("t5");

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();

        System.out.println("waiting...");
        latch.await();
        System.out.println("all end");
    }
}
