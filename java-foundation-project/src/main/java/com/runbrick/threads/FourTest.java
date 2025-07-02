package com.runbrick.threads;

import java.util.concurrent.*;

public class FourTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println("------------------主线程开始------------------");
        // 这里我只实现了一个简单的线程池，如果正式使用不能用这个来实现线程池必须要用 new ThreadPoolExecutor();
        ExecutorService executorService = Executors.newCachedThreadPool();

        executorService.execute(new RunnableTest());

        Future<String> submit = executorService.submit(new CallableTest());
        System.out.println(submit.get());

        executorService.shutdown();
    }
}


class RunnableTest implements Runnable {
    @Override
    public void run() {
        System.out.println("调用了 Runnable 的方法");
    }
}

class CallableTest implements Callable<String> {
    @Override
    public String call() throws Exception {
        return "我是 Callable 返回值";
    }
}