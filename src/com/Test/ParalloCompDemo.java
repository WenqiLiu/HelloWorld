/**
 * 并行找出一段数字内的所有prime number，返回一个list.
 *
 * 要使用ThreadPool，且需要返回值，故要 implements Callable<List<Integer>> 接口。
 *
 * ThreadPool中，callable 对应 submit(Callable c);
 */

package com.Test;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

public class ParalloCompDemo {

    final static int cupCoreNum = 2;

    static List<Integer> getPrime (int s, int e) {
        List<Integer> list = new LinkedList<> ();

        for (int i = s; i < e; i++) {
            if (i % 2 == 0) {
                continue;
            }
            list.add(i);
        }
        return list;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ExecutorService service = Executors.newFixedThreadPool (cupCoreNum);

        MyTask t1 = new MyTask (1, 5000);
        MyTask t2 = new MyTask (5001, 7000);
        MyTask t3 = new MyTask (7001, 9000);
        MyTask t4 = new MyTask (9001, 10000);

        Future<List<Integer>> f1 = service.submit (t1);
        Future<List<Integer>> f2 = service.submit (t2);
        Future<List<Integer>> f3 = service.submit (t3);
        Future<List<Integer>> f4 = service.submit (t4);

        Long start = System.currentTimeMillis ();
        f1.get ();
        f2.get ();
        f3.get ();
        f4.get ();
        Long end = System.currentTimeMillis ();
        System.out.println (end - start);

    }

    static class MyTask implements Callable <List<Integer>> {
        int start, end;

        public MyTask (int s, int e) {
            start = s;
            end = e;
        }

        @Override
        public List<Integer> call() throws Exception {

            return getPrime(start, end);

        }
    }

}
