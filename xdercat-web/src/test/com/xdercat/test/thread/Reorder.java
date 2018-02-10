package com.xdercat.test.thread;

/**
 * Created by bibom on 2/1/18.
 */
public class Reorder {
    int a = 0;
    boolean flag = false;

    /**
     * 线程1执行该方法
     */
    public void writer() {
        a = 1;
        flag = true;
    }

    /**
     * 线程2执行该方法
     */
    public void reader() {
        int i = 0;
        if (flag) {
            i = a * a;
            System.out.println(i);
        }
    }

    public static void main(String[] args) {
        //证明writer方法中，a=1;flag=true;的顺序会被编译器或处理器重排序
        //两个线程之间不存在数据依赖性
        //如果使用synchronized修饰reader和writer方法，程序就会按顺序一致性内存模式执行
        for (int i = 0; i < 100000;i++) {
            final Reorder reorder = new Reorder();
            Thread thread1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    reorder.writer();
                }
            });
            Thread thread2 = new Thread(new Runnable() {
                @Override
                public void run() {
                    reorder.reader();
                }
            });
            thread1.start();
            thread2.start();
        }
    }
}
