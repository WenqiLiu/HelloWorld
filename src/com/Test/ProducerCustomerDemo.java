package com.Test;
import java.sql.Time;
import java.util.concurrent.TimeUnit;

public class ProducerCustomerDemo {

    public static void main(String[] args) {

        Food food = new Food ();

        Producer p1 = new Producer (food);
        Customer c1 = new Customer (food);

        Thread tp = new Thread (p1);
        Thread tc = new Thread (c1);

        tp.start();
        tc.start();
    }
}

/**
 * Producer produces food, 20times, even has Burger, odd number has Pizza. ß
 */

class Producer implements Runnable {

    private Food food;

    public Producer(Food food) {
        this.food = food;
    }

    @Override
    public void run() {

        for (int i = 0; i < 20; i++) {

            try {
                TimeUnit.SECONDS.sleep (1);
            } catch (InterruptedException e) {
                e.printStackTrace ();
            }

            if (i % 2 == 0) {
                this.food.cookFood("Burger", "Cal: 1200");
            }
            else {
                this.food.cookFood("Pizza", "Cal: 960");
            }

//            try {
//                TimeUnit.SECONDS.sleep (1);
//            } catch (InterruptedException e) {
//                e.printStackTrace ();
//            }

            System.out.println(Thread.currentThread().getName() + " cooked " + this.food.name+"--"+this.food.desc);
        }
    }
}

/**
 * Customer consumers food when it is ready.
 */

class Customer implements Runnable {
    private Food food;

    public Customer(Food food) {
        this.food = food;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            try {
                TimeUnit.MILLISECONDS.sleep (200);
            }
            catch (InterruptedException e) {
                e.printStackTrace ();
            }

            this.food.serveFood ();

        }
    }
}

/**
 * Define Food class.
 */

class Food {
    String name;
    String desc;
    private boolean cooked = false;

    public synchronized void cookFood (String name, String desc) {

        if (cooked) {
            try {
                this.wait ();
            } catch (InterruptedException e) {
                e.printStackTrace ();
            }
        }

        this.setName(name);
        //cooking
        try {
            Thread.sleep(650);
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
        this.setDesc(desc);
        cooked = true;
        this.notify();
    }

    public synchronized void serveFood() {

        if (!cooked) {
            try{
                this.wait ();
            }
            catch (InterruptedException e) {
                e.printStackTrace ();
            }
        }

        System.out.println (Thread.currentThread ().getName () + " consumers " + this.getName()+"--"+this.getDesc());

        cooked = false;
        this.notify ();
    }

    public Food(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    public Food() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
