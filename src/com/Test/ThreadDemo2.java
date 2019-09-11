package com.Test;

public class ThreadDemo2 {

    public static void main (String[] args) {

        myRunnable2 mr2 = new myRunnable2();
        Thread t = new Thread(mr2);
        t.start();

        for (int i = 0; i < 10; i++) {
            System.out.println(Thread.currentThread().getName() + "-" + i);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (i == 6) {
                mr2.flag = true;
            }
        }
    }
}

class myRunnable2 implements Runnable {

    boolean flag = false;

    public void run() {

        for (int i = 0; i < 10; i++) {
            if (flag) {
                break;
            }
            System.out.println(Thread.currentThread().getName() + "-" + i);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
     }
}
