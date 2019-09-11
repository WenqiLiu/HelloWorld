package com.Test;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

public class ForkJoinDemo {

    static int[] nums = new int[100000];
    final static int echo_size = 5000;
    static Random r = new Random ();

    static  {
        for (int i = 0; i < nums.length; i++) {
            nums[i] = r.nextInt (100);
        }
    }

   /* static class addTask extends RecursiveAction {

        int start, end;

        public addTask (int a, int b) {
            this.start = a;
            this.end = b;
        }

        @Override
        protected void compute() {
            Long sum = 0l;

            if (end - start <= echo_size) {
                for (int i = start; i < end; i++) {
                    sum += nums[i];
                }
                System.out.println ("From "+start+" to " + end +": " + sum);
                //return;
            } else {

                int mid = (end - start) / 2 + start;
                addTask subTask1 = new addTask (start, mid);
                addTask subTask2 = new addTask (mid + 1, end);

                //start subtasks
                subTask1.fork ();
                subTask2.fork ();
            }
        }
    }

    */

    static class addTask extends RecursiveTask <Long> {

        int start, end;

        public addTask (int a, int b) {
            this.start = a;
            this.end = b;
        }

        @Override
        protected Long compute() {

            if (this.end - this.start <= echo_size) {
                Long sum = 0l;
                for (int i = start; i < end; i++) {
                    sum += nums[i];
                }
                return sum;
            }

            int mid = (end - start) / 2 + start;
            addTask subTask1 = new addTask (start,mid);
            addTask subTask2 = new addTask(mid + 1, end);

            subTask1.fork ();
            subTask2.fork ();

            return subTask1.join () + subTask2.join();
        }
    }


    public static void main(String[] args) throws IOException {
        ForkJoinPool fjd = new ForkJoinPool ();
        addTask task = new addTask (0, nums.length);
        fjd.execute(task);
        System.in.read ();
    }
}
