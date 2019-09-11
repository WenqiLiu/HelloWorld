package com.Test;

import java.util.concurrent.locks.ReentrantLock;

public class ThreadDemo4 {

    public static void main(String[] args) {

        SaleThread st = new SaleThread();

        Thread t1 = new Thread(st);
        Thread t2 = new Thread(st);

        t1.start();
        t2.start();

    }
}


class SaleThread implements Runnable {
    private int ticket = 10;

    public void run() {
        for (int i = 0; i < 100; i++) {
            method_lock();
            //method();
        }
    }

    // using Reentrantlock to sync (互斥锁)
    ReentrantLock lock = new ReentrantLock();

    private void method_lock() {
        try {

            lock.lock();
            if (ticket > 0) {
                System.out.println(Thread.currentThread().getName() + " will sale ticket #" + ticket);
                ticket--;
            }
            Thread.sleep(500);

        } catch (InterruptedException e)  {
            e.printStackTrace();
        }
        finally {
            lock.unlock();
        }
    }

    // using syc
    private synchronized void method() {
        if (ticket < 1) {
            return;
        }

        System.out.println(Thread.currentThread().getName() + " will sale ticket #" + ticket);
        ticket--;
        try {
            Thread.sleep(500);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}