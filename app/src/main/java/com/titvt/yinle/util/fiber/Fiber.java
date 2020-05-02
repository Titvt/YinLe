package com.titvt.yinle.util.fiber;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Fiber {
    private static Fiber instance;
    private ThreadPoolExecutor executor = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors() * 2 + 1, Integer.MAX_VALUE, 5L, TimeUnit.MINUTES, new LinkedBlockingQueue<>());

    public static Fiber getInstance() {
        if (instance == null) {
            synchronized (Fiber.class) {
                if (instance == null)
                    instance = new Fiber();
            }
        }
        return instance;
    }

    public void run(Runnable runnable) {
        executor.execute(runnable);
    }
}
