package com.Test;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class MyContainer2 <T> {

    final private LinkedList<T> list = new LinkedList<T> ();
    final private int MAX_SIZE = 10;
    private int count = 0;

    private ReentrantLock lock = new ReentrantLock();
    private Condition producer = lock.newCondition ();
    private Condition consumer = lock.newCondition ();


    public void put(T t) {

        while (list.size() == MAX_SIZE) {
            try {
                lock.lock ();

                while (list.size() == MAX_SIZE) {
                    producer.await ();
                }

                list.add(t);
                count++;
                consumer.signalAll (); //成功生产，唤醒所有 consumer。
            }
            catch (InterruptedException e) {
                e.printStackTrace ();
            }
            finally {
                lock.unlock ();
            }
        }
    }

    public T get() {
        T t = null;

        try {
            lock.lock ();

            while (list.size() == 0) {
                consumer.await();
            }

            t = list.removeFirst ();
            count--;
            producer.signalAll(); // 成功消费，唤醒所有producer。

        }
        catch (InterruptedException e) {
            e.printStackTrace ();
        }
        finally {
            lock.unlock ();
        }
        return t;
    }

    public static void main(String[] args) {
        MyContainer2<String> c = new MyContainer2 ();

        //creat and start consumer threads
        for (int i = 0; i < 10; i++) {
            new Thread (() -> {

                for (int j = 0; j < 5; j++) {
                    System.out.println (Thread.currentThread ().getName ()+" "+c.get ());
                }

            }, "c"+i).start ();
        }

        try{
            TimeUnit.MILLISECONDS.sleep (200);
        }
        catch (InterruptedException e) {
            e.printStackTrace ();
        }

        //creat producers and start.
        for (int i = 0; i < 2; i++) {
            new Thread (() -> {

                for (int j = 0; j < 5; j++) {
                    c.put(Thread.currentThread ().getName () + " " + j);
                }
            }, "p"+i).start ();
        }
    }
}
