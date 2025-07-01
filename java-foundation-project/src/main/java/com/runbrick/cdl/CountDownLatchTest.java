package com.runbrick.cdl;

import java.util.concurrent.*;

public class CountDownLatchTest {

    public static void main(String[] args) throws InterruptedException {

        int corePoolSize = 10;
        int maxPoolSize = 10;
        long keepAliveTime = 0L;
        TimeUnit unit = TimeUnit.MILLISECONDS;

        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(100);
        // 强烈不推荐直接使用 Executors.newFixedThreadPool。这是阿里巴巴《Java开发手册》中明确禁止的。
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                corePoolSize,
                maxPoolSize,
                keepAliveTime,
                unit,
                workQueue,
                new ThreadPoolExecutor.CallerRunsPolicy()
        );

        // 2. 创建 CountDownLatch，计数为 5，表示需要等待 5 个工作线程完成
        final int workerCount = 5;
        CountDownLatch latch = new CountDownLatch(workerCount);
        System.out.println("老板：项目启动，请各位员工开始工作...");
        // 3. 创建并启动 5 个工作线程
        for (int i = 1; i <= workerCount; i++) {
            executor.submit(new Worker(i, latch));
        }
        // 4. 主线程（老板）调用 await() 方法，等待所有员工完成工作
        System.out.println("老板：我正在等待所有员工完成工作...");
        latch.await(); // 阻塞在这里，直到 latch 的 count 减到 0
        // 5. 所有员工都完成了工作，主线程从 await() 返回，继续执行
        System.out.println("老板：所有员工都已完成工作！项目进入下一阶段。");
        // 6. 关闭线程池
        executor.shutdown();

        try {
            // 2. 等待一段时间，让现有任务执行完毕
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                // 3. 如果超时，强制关闭
                executor.shutdownNow();
                // 4. 再次等待，以响应强制关闭
                if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                    System.err.println("Pool did not terminate");
                }
            }
        } catch (InterruptedException ie) {
            // (Re-)Cancel if current thread also interrupted
            executor.shutdownNow();
            // Preserve interrupt status
            Thread.currentThread().interrupt();
        }
    }
}
