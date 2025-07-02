package com.runbrick.threads;

import java.util.concurrent.TimeUnit;

public class Back {
    public static void main(String[] args) throws InterruptedException {
        CounterTask sharedTask = new CounterTask();

        Thread thread = new Thread(sharedTask, "CounterTask");
        Thread thread2 = new Thread(sharedTask, "CounterTask2");

        thread.start();
        thread2.start();

        thread.join();
        thread2.join();

        System.out.println("线程执行完毕");
        System.out.println("预计结果为： 200");
        System.out.println("实际结果为：" + sharedTask.getCount()); // 实际可能小于 200
    }

}

class CounterTask implements Runnable {
    private int count = 0; // 共享资源

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            try {
                // 模拟这个计数增加耗时 100 ms
                TimeUnit.MILLISECONDS.sleep(100);
                count++;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public int getCount() {
        return count;
    }
}

