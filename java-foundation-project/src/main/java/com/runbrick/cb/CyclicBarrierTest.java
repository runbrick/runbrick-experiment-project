package com.runbrick.cb;

import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class CyclicBarrierTest {

    private static final int RUNNER_COUNT = 5;
    private static final int RACE_COUNT   = 4;

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(RUNNER_COUNT);
        final AtomicInteger currentRace = new AtomicInteger(1);


        // 每轮比赛结束所有选手的完成时间
        final Map<Integer, Long> raceResults = new ConcurrentHashMap<>();
        // 每轮比赛结束，进行裁判操作
        Runnable refereeAction = refereeAction(currentRace, raceResults);
        final CyclicBarrier barrier = new CyclicBarrier(RUNNER_COUNT, refereeAction);


        System.out.println("操场比赛即将开始，本场将决出每轮的冠军！");
        for (int i = 0; i < RUNNER_COUNT; i++) {
            final int runnerId = i + 1;
            executor.submit(() -> {
                try {
                    for (int raceNum = 1; raceNum <= RACE_COUNT; raceNum++) {
                        // 模拟跑步并记录用时
                        long startTime = System.nanoTime(); // 使用纳秒更精确
                        long runTimeMs = ThreadLocalRandom.current().nextLong(1000, 4000);
                        System.out.printf("赛跑者 %d 正在进行第 %d 场比赛...%n", runnerId, raceNum);
                        Thread.sleep(runTimeMs);
                        long endTime = System.nanoTime();
                        long timeTakenNanos = endTime - startTime;
                        System.out.printf(">>> 赛跑者 %d 完成了第 %d 场比赛, 用时 %dms. 在终点等待...%n", runnerId, raceNum, runTimeMs);
                        // 4. 在等待前，记录自己的成绩
                        raceResults.put(runnerId, timeTakenNanos);
                        // 在屏障处等待
                        barrier.await();
                    }
                    System.out.printf("**** 赛跑者 %d 跑完了所有比赛！****%n", runnerId);
                } catch (InterruptedException | BrokenBarrierException e) {
                    System.err.printf("赛跑者 %d 的比赛出现意外，已退出。%n", runnerId);
                    Thread.currentThread().interrupt();
                }
            });
        }
        executor.shutdown();
    }

    private static Runnable refereeAction(AtomicInteger currentRace, Map<Integer, Long> raceResults) {
        Runnable refereeAction = () -> {
            int raceNum = currentRace.get();
            System.out.println("---------------------------------------------------------");
            System.out.printf("裁判: 第 %d 场比赛所有选手均已到达！正在计算成绩...%n", raceNum);
            // 寻找获胜者
            long minTime = Long.MAX_VALUE;
            int winnerId = -1;
            for (Map.Entry<Integer, Long> entry : raceResults.entrySet()) {
                if (entry.getValue() < minTime) {
                    minTime = entry.getValue();
                    winnerId = entry.getKey();
                }
            }
            long minTimeMs = TimeUnit.NANOSECONDS.toMillis(minTime); // 转换为毫秒更易读
            System.out.printf("🏆 第 %d 场比赛的冠军是: 赛跑者 %d! 用时: %dms 🏆%n", raceNum, winnerId, minTimeMs);
            System.out.println("---------------------------------------------------------");
            // 3. 为下一场比赛做准备：清空本次成绩
            raceResults.clear();
            if (raceNum < RACE_COUNT) {
                System.out.printf("准备开始第 %d 场比赛...%n%n", raceNum + 1);
            } else {
                System.out.println("所有比赛已完成! 感谢各位的精彩表现!");
            }
            currentRace.incrementAndGet();
        };
        return refereeAction;
    }
}
