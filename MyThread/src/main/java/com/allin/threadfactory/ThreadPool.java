package com.allin.threadfactory;

import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPool {

    private static volatile ThreadPool threadPool;

    private ThreadPool(){

    }

    public static ThreadPool getThreadPool() {
        if (threadPool == null){
            synchronized (ThreadPool.class){
                if (threadPool == null){
                    threadPool = new ThreadPool();
                }
            }
        }
        return threadPool;
    }

    //参数初始化
    private  final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    //核心线程数量大小
    private final int CORE_POOL_SIZE = Math.max(2, Math.min(CPU_COUNT - 1, 4));
    private final int MAX_POOL_SIZE = 1024;
    private final int KEEP_ALIVE_TIME = 60;
    private ThreadPoolExecutor executor = new ThreadPoolExecutor(
            CORE_POOL_SIZE,
            MAX_POOL_SIZE,
            KEEP_ALIVE_TIME,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>()
    );

    public Executor getExecutor() {
        return executor;
    }

    public void execute(Runnable command) {
        executor.execute(command);
    }

    public void remove(Runnable command) {
        executor.remove(command);
    }

    public Future<?> submit(Runnable command) {
       return executor.submit(command);
    }

}
