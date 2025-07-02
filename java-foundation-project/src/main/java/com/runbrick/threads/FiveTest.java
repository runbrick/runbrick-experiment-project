package com.runbrick.threads;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class FiveTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        CompletableFuture.runAsync();
        CompletableFuture<String> stringCompletableFuture = CompletableFuture.supplyAsync(() -> "hello");
        String s = stringCompletableFuture.get();
        System.out.println(s);
    }
}

