package com.tangzy.tzymvp.util;

import android.util.Log;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolUtil {
    //参数初始化
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    //核心线程数量大小
    private static final int CORE_POOL_SIZE = Math.max(2, Math.min(CPU_COUNT - 1, 4));
    //适用于IO密集型
//    private static final int MAX_POOL_SIZE = 501;
    private static final int MAX_POOL_SIZE = CPU_COUNT * 4 + 1;
//    private static final int MAX_POOL_SIZE = CPU_COUNT * 2 + 1;
    private static final int QUEUE_CAPACITY = 1024*4;
    private static final int KEEP_ALIVE_TIME = 30;
    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(
            CORE_POOL_SIZE,
            MAX_POOL_SIZE,
            KEEP_ALIVE_TIME,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>());

    public static void execute(Runnable command) {
        Log.d("hhhhhhhhh", "cpu = "+MAX_POOL_SIZE);


        executor.execute(command);
    }
    public static Executor getExecutor() {
        return executor;
    }

}
