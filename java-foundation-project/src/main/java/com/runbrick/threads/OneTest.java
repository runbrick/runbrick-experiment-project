package com.runbrick.threads;


public class OneTest {

    public static void main(String[] args) {
        System.out.println("主线程开始...");
        MyThread thread1 = new MyThread();
        thread1.setName("A"); // 可以给线程设置名字
        MyThread thread2 = new MyThread();
        thread2.setName("B");

        thread1.start();
        thread2.start();

        System.out.println("主线程结束...");
    }
}


/**
 * 创建一个类，让它继承自 java.lang.Thread 类。
 * 重写 run() 方法，这个方法包含了线程需要执行的任务代码。
 * 创建该子类的实例。
 * 调用实例的 start() 方法来启动线程。注意： 是调用 start() 而不是 run()，start() 会创建一个新线程并执行 run() 方法，而直接调用 run() 只是在当前线程中执行一个普通方法。
 */
class MyThread extends Thread {
    @Override
    public void run() {
        System.out.println("当前线程为：" + Thread.currentThread().getName());
    }
}