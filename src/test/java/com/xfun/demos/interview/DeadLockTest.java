package com.xfun.demos.interview;

/**
 * Created by xfun on 4/16/17.
 */
public class DeadLockTest{

    public static void main(String[] args) {
        Thread t1 = new Thread(new DeadLock(1));
        Thread t2 = new Thread(new DeadLock(2));
        t1.start();
        t2.start();
    }

}

class DeadLock implements Runnable{

    private int flag;

    private static Object o1 = new Object();
    private static Object o2 = new Object();

    DeadLock(int _flag){
        this.flag = _flag;
    }

    public void run() {
        System.out.println("flag: " + flag);
        if(flag == 1){
            synchronized(o1){
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized(o2){
                    System.out.println("1");
                }
            }
        }
        else if(flag == 2){
            synchronized(o2){
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized(o1){
                    System.out.println("2");
                }
            }
        }
    }
}
