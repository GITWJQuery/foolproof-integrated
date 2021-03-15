package com.select.integrated.test;

public class ImplRunnableThread {
    public static void main(String[] args) {
        MyThread m1 = new MyThread("线程A");
        MyThread m2 = new MyThread("线程B");
        Thread t1 = new Thread(m1);
        Thread t2 = new Thread(m2);
        t1.start();
        t2.start();
    }
}

class MyThread implements Runnable {
    private String name;
    public MyThread(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println(name + "运行, i = " + i);
        }
    }
}
