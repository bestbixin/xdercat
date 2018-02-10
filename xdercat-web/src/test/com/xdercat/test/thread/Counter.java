package com.xdercat.test.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by bibom on 2/1/18.
 */
public class Counter {
    private AtomicInteger atomicI = new AtomicInteger(0);
    private int i = 0;

    public static void main(String[] args) {
        final Counter counter = new Counter();
        List<Thread> threadList = new ArrayList<Thread>(600);
        long start = System.currentTimeMillis();
        for (int j = 0; j < 100; j++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 10000; i++) {
                        counter.count();
                        counter.safeCount();
                    }
                }
            });
            threadList.add(thread);
        }

        for (Thread t : threadList) {
            t.start();
        }
        for (Thread t : threadList) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(counter.i);
        System.out.println(counter.atomicI.get());
        System.out.println(System.currentTimeMillis() - start);
    }

    private void safeCount() {
        for (;;) {
            int i = atomicI.get();
            boolean suc = atomicI.compareAndSet(i, ++i);
            if (suc) {
                break;
            }
        }
    }

    private void count() {
        i++;
    }


}
