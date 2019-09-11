package com.Test;

public class ThreadDemo1 {

    public static void main(String[] args) {

        myThread t1 = new myThread();

        t1.start();

        myRunnable mr = new myRunnable();
        Thread tr = new Thread(mr);
        tr.start();
    }
}

/**
 * implements Runnable interface to implement multi-threads.
 *
 * Thread.sleep(): thread NOT lost monitor (lock)
 */

class myRunnable implements Runnable {
    public void run(){
        for (int i = 0; i < 10; i++) {
            System.out.println(Thread.currentThread().getName() + "-" + i);
            try {
                Thread.sleep(500);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}

/**
 *
 */

class myThread extends Thread{

    @Override
    public void run(){
        for (int i = 0; i < 10; i++) {
            System.out.println(Thread.currentThread().getName() + "-" + i);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}