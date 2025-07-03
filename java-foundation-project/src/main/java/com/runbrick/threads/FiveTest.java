package com.runbrick.threads;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class FiveTest {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        System.out.println("主线程启动");
        // 创建一个 CompletableFuture 对象，实现无返回结果
//        CompletableFuture.runAsync(() -> {
//            try {
//                TimeUnit.SECONDS.sleep(1);
//                System.out.println("1231231");
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//
//        });

        // 创建一个 CompletableFuture 获取结果
//        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> "hello world");
//        System.out.println(future1.get());


        // 实现一个复杂的 CompletableFuture
//        CompletableFuture.supplyAsync(() -> {
//            int a = 0;
//            for (int i = 0; i < 10; i++) {
//                a++;
//            }
//            return a;
//        }).thenApply(f -> f + 1).thenAccept(f -> {
//            try {
//                TimeUnit.SECONDS.sleep(10);
//                System.out.println("f = " + f);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        }).thenRun(() -> {
//            System.out.println("任务执行完毕");
//        });


        // 组合多个CompletableFuture

//        CompletableFuture<Double> oneFuture = CompletableFuture.supplyAsync(() -> {
//            try {
//                TimeUnit.SECONDS.sleep(2);
//            } catch (Exception e) {
//            }
//            return 100.0;
//        });
//        CompletableFuture<Double> twoFuture = CompletableFuture.supplyAsync(() -> {
//            try {
//                TimeUnit.SECONDS.sleep(1);
//            } catch (Exception e) {
//            }
//            return 0.85;
//        });
//        // 使用 thenCombine 组合两个CompletableFuture
//        CompletableFuture<Double> finalPriceFuture = oneFuture.thenCombine(
//                twoFuture,
//                (one, two) -> {
//                    return one + two;
//                }
//        );
//        System.out.println(finalPriceFuture.get()); // 输出: 最终价格是: 85.0

        // 等待所有任务完成
        CompletableFuture<String> f1 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (Exception e) {
            }
            return "用户数据";
        });
        CompletableFuture<String> f2 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (Exception e) {
            }
            return "新闻列表";
        });
        CompletableFuture<String> f3 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (Exception e) {
            }
            return "天气预报";
        });


        CompletableFuture<Void> allFutures = CompletableFuture.allOf(f1, f2, f3);


        allFutures.join();
        System.out.println("所有并行任务都已完成！");
        System.out.println(f1.get());
        System.out.println(f2.get());
        System.out.println(f3.get());

        // 这里延时了很久，如果执行完了自己结束进程
        TimeUnit.SECONDS.sleep(100);
        System.out.println("主线程结束");
    }
}

