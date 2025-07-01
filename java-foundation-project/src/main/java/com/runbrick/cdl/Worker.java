package com.runbrick.cdl;

import java.util.concurrent.CountDownLatch;

public class Worker implements Runnable {
    private final int            id;
    private final CountDownLatch latch;

    Worker(int id, CountDownLatch latch) {
        this.id = id;
        this.latch = latch;
    }

    @Override
    public void run() {
        try {
            // 模拟工作耗时
            System.out.println("员工 " + id + " 正在努力工作中...");
            Thread.sleep((long) (Math.random() * 2000 + 1000)); // 随机工作 1-3 秒
            System.out.println("员工 " + id + " 完成了工作！");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // 重新设置中断状态
            System.err.println("员工 " + id + " 在工作中被中断了。");
        } finally {
            // 工作完成后，调用 countDown() 方法，通知 CountDownLatch
            latch.countDown();
            System.out.println("员工 " + id + " 报告完成，当前剩余任务数: " + latch.getCount());
        }
    }
}

