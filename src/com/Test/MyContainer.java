package com.Test;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

public class MyContainer<T> {

    final private Integer MAX_SIZE = 10;
    final private LinkedList<T> list = new LinkedList<> ();
    private Integer count = 0;

    public synchronized void put(T t){

        while (this.count == MAX_SIZE) { // 不能使用 if + wait(), 进入wait（）之后，若有多个线程，当一个线程获得锁之后其他线程会出问题
            try{                         // 因为进入while循环之后，线程开始wait，不过当线程唤醒之后不会再向上重新判断条件！！
                this.wait ();
            }
            catch (InterruptedException e) {
                e.printStackTrace ();
            }
        }

        this.list.add (t);
        this.count++;
        this.notifyAll(); //不用notify()。避免只叫醒另一个生产者

    }

    public synchronized T get() {

        T t = null;

        while (count == 0) {
            try{
                this.wait ();
            }
            catch (InterruptedException e) {
                e.printStackTrace ();
            }
        }

        t = list.removeFirst ();
        count--;
        this.notifyAll ();

        return t;
    }

    public static void main(String[] args) {
        MyContainer<String> mc = new MyContainer ();

        //Init and start 10 consumers, each consumer consumer 5 items from the container.
        for (int i = 0; i < 10; i++) {
            new Thread (() -> {
                for (int j = 0; j < 5; j++) {
                        System.out.println (Thread.currentThread ().getName ()+" consumes " + mc.get ());
                }
            }, "c" + i).start ();
        }

        try{
            TimeUnit.MILLISECONDS.sleep (200);
        }
        catch (InterruptedException e) {
            e.printStackTrace ();
        }

        //Init and start 2 producer tasks, each producer produces 25 items to the container

        for (int i = 0; i < 2; i++) {
            new Thread (() -> {
                for (int j = 0; j < 25; j++) {
                    mc.put (Thread.currentThread ().getName () + "--" + j);
                }
            }, "p" + i).start ();
        }
    }
}
