package com.runbrick.lifecycle;


/**
 * 新建(New)
 * 可运行(Runnable)
 * 阻塞(Blocking)
 * 无限期等待(Waiting)
 * 限期等待(Timed Waiting)
 * 死亡(Terminated/Dead)
 */
public class OneTest {
    // 一个共享的锁对象，用于演示 BLOCKED 和 WAITING 状态
    private static final Object sharedLock = new Object();

    public static void main(String[] args) throws InterruptedException {
        System.out.println("--- 演示线程生命周期 ---");
        // 路径 1: New -> Runnable
        //======================================================================
        Thread thread1 = new Thread(() -> {
            // 这个线程创建后会立即进入 RUNNABLE 状态并结束
            printState(Thread.currentThread(), "进入 run() 方法，即将结束。");
        }, "Thread-1 (New to Runnable)");

        System.out.println("\n【场景一：New -> Runnable】");
        printState(thread1, "线程已创建，还未调用 start()"); // --> NEW
        thread1.start();
        // 短暂等待，让 thread1 有机会运行
        Thread.sleep(100);
        printState(thread1, "调用 start() 后，线程可能已执行完。"); // --> TERMINATED (因为它太快了)
        // 路径 2: Runnable -> Timed Waiting -> Runnable
        //======================================================================
        Thread thread2 = new Thread(() -> {
            try {
                printState(Thread.currentThread(), "准备调用 Thread.sleep(2000)。");
                Thread.sleep(2000); // 进入 TIMED_WAITING
                printState(Thread.currentThread(), "从 sleep 中醒来。");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "Thread-2 (Timed Waiting)");
        System.out.println("\n【场景二：Runnable -> Timed Waiting】");
        thread2.start();
        Thread.sleep(500); // 确保 thread2 已经开始 sleep
        printState(thread2, "主线程观察到 thread2 正在 sleep。"); // --> TIMED_WAITING
        // 路径 3: Runnable -> Blocked -> Runnable
        //======================================================================
        System.out.println("\n【场景三：Runnable -> Blocked】");
        Thread thread3 = new Thread(() -> {
            printState(Thread.currentThread(), "准备进入 synchronized 代码块。");
            synchronized (sharedLock) {
                printState(Thread.currentThread(), "成功获取锁，进入 synchronized 代码块！");
            }
        }, "Thread-3 (Blocked)");
        synchronized (sharedLock) {
            printState(thread3, "主线程已持有锁，准备启动 thread3。");
            thread3.start();
            Thread.sleep(500); // 确保 thread3 已经启动并尝试获取锁
            printState(thread3, "主线程持有锁，thread3 正在等待锁。"); // --> BLOCKED
        } // 主线程在这里释放锁

        Thread.sleep(500); // 等待 thread3 获取锁并执行完毕
        printState(thread3, "主线程释放锁后，thread3 已执行完毕。"); // --> TERMINATED
        // 路径 4: Runnable -> Waiting -> Runnable
        //======================================================================
        System.out.println("\n【场景四：Runnable -> Waiting】");
        Thread thread4 = new Thread(() -> {
            synchronized (sharedLock) {
                try {
                    printState(Thread.currentThread(), "获取锁成功，准备调用 lock.wait()。");
                    sharedLock.wait(); // 进入 WAITING
                    printState(Thread.currentThread(), "被 notify 唤醒，重新获取锁。");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "Thread-4 (Waiting)");
        thread4.start();
        Thread.sleep(500); // 确保 thread4 已经启动并调用了 wait()
        printState(thread4, "thread4 调用了 wait()。"); // --> WAITING
        synchronized (sharedLock) {
            printState(thread4, "主线程获取锁，准备 notify。");
            sharedLock.notify(); // 唤醒 thread4
            printState(thread4, "主线程调用 notify()，但 thread4 仍需等待主线程释放锁。"); // --> BLOCKED
        } // 主线程释放锁

        Thread.sleep(500); // 等待 thread4 重新获取锁并执行完毕
        printState(thread4, "主线程释放锁后，thread4 已执行完毕。"); // --> TERMINATED
        // 路径 5 & 6: Runnable -> Terminated (正常 vs 异常)
        //======================================================================
        System.out.println("\n【场景五和六：Runnable -> Terminated】");

        // 正常结束
        Thread thread5 = new Thread(() -> {
            printState(Thread.currentThread(), "我将正常执行完毕。");
        }, "Thread-5 (Normal Finish)");
        thread5.start();
        thread5.join(); // 等待 thread5 执行结束
        printState(thread5, "正常执行完毕后。"); // --> TERMINATED

        // 异常结束
        Thread thread6 = new Thread(() -> {
            printState(Thread.currentThread(), "我将抛出一个异常！");
            throw new RuntimeException("演示异常导致线程终止");
        }, "Thread-6 (Exception Finish)");
        thread6.start();
        thread6.join(); // 等待 thread6 执行结束
        printState(thread6, "抛出未捕获异常后。"); // --> TERMINATED
        System.out.println("\n--- 演示结束 ---");
    }

    /**
     * 一个辅助方法，用于格式化打印线程的状态。
     * @param t 要观察的线程
     * @param scenario 当前场景的描述
     */
    private static void printState(Thread t, String scenario) {
        System.out.printf("  [观察] 线程 '%s' - 状态: %-15s - 场景: %s%n",
                t.getName(), t.getState(), scenario);
    }
}
