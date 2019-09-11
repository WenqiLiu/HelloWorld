package com.Test;

import java.sql.Time;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolDemo {

    public static void main(String[] args) {

        ExecutorService service = Executors.newFixedThreadPool (5);

        System.out.println (service);

        for (int i = 0; i < 10; i++) {

            service.execute (() -> {
                try {
                    System.out.println (Thread.currentThread ().getName ());
                    TimeUnit.MILLISECONDS.sleep (200);
                }
                catch (InterruptedException e ) {
                    e.printStackTrace ();
                }
            });

        }
    }
}
