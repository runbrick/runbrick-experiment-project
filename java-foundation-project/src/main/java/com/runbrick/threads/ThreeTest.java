package com.runbrick.threads;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class ThreeTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        MyCallable myCallable = new MyCallable();
        // 4. 使用FutureTask包装Callable
        FutureTask<String> futureTask = new FutureTask<>(myCallable);
        // FutureTask也实现了Runnable接口，所以可以传给Thread
        Thread thread = new Thread(futureTask, "children");
        thread.start();
        // 5. 获取线程执行结果
        // get()方法会阻塞当前线程，直到call()方法执行完毕返回结果
        System.out.println("任务开始执行");
        String result = futureTask.get();
        System.out.println("子线程执行完毕，结果是: " + result);

        System.out.println("主线程结束...");
    }
}

/**
 * 实现Callable接口和实现Runnable接口的区别：
 * 1. Callable接口有返回值，Runnable接口没有返回值
 * 2. Callable接口抛出异常，Runnable接口不能抛出异常
 */
class MyCallable implements Callable<String> {
    @Override
    public String call() throws Exception {
        Thread.sleep(2000); // 模拟耗时计算
        return Thread.currentThread().getName() + "线程执行成功了";
    }
}
