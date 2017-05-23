package com.xfun.demos.interview;

import java.util.concurrent.Semaphore;

/**
 * Created by xfun on 5/16/17.
 */
public class SemaphoreTest {
    public static void main(String[] args) throws InterruptedException {

        final Semaphore semaphore = new Semaphore(3);

        Runnable job = new Runnable() {
            public void run() {
                try {
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + " - acquire");
                    Thread.sleep(200);
                    System.out.println(Thread.currentThread().getName() + " --------- release");
                    semaphore.release();
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

        for(;;){
            new Thread(job).start();
            Thread.sleep(1000);
        }

    }
}
