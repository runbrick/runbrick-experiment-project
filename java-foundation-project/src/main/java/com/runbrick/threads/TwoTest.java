package com.runbrick.threads;

/**
 *
 */
public class TwoTest {

    public static void main(String[] args) {
        System.out.println("主线程开始...");
        Thread thread = new Thread(new MyRunnable(), "A");
        Thread thread2 = new Thread(new MyRunnable(), "B");

        thread.start();
        thread2.start();

        System.out.println("主线程结束...");
    }
}


/**
 * 创建一个类，让它实现 java.lang.Runnable 接口。
 * 重写 run() 方法，这个方法包含了线程需要执行的任务代码。
 * 创建该子类的实例。
 * 调用实例的 start() 方法来启动线程。注意： 是调用 start() 而不是 run()，start() 会创建一个新线程并执行 run() 方法，而直接调用 run() 只是在当前线程中执行一个普通方法。
 */
class MyRunnable extends MyRunnaleParent implements Runnable {
    @Override
    public void run() {
        System.out.println("当前线程为：" + Thread.currentThread().getName() + "父类为：" + name);
    }
}

/**
 *
 */
class MyRunnaleParent {
    protected String name = "Parent";
}

