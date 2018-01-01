package com.xfun.demo.interview;

/**
 * Created by xfun on 5/16/17.
 */
public class ThreadJoinTest {
    public static void main(String[] args) throws InterruptedException {

        Runnable job = new Runnable() {
            public void run() {
                System.out.println(Thread.currentThread().getName());
                try {
                    Thread.sleep(3000);
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

        t1.start();t1.join(1000);
        t2.start();t2.join(1000);
        t3.start();t3.join();
        t4.start();t4.join();
        t5.start();
    }
}