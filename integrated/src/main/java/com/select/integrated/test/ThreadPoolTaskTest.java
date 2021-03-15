package com.select.integrated.test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
public class ThreadPoolTaskTest {

    public static void main(String[] args) throws Exception {
        thread();
    }

    private static void thread() throws Exception {
        long start = System.currentTimeMillis();
        List<Integer> results = Collections.synchronizedList(new ArrayList<>(10));

        ThreadPoolTaskExecutor executor = threadPoolTaskExecutor();
        List<Runnable> enables = new ArrayList<>();
        CountDownLatch countDownLatch = new CountDownLatch(10);

        log.info("添加任务");
        for (int i = 0; i < countDownLatch.getCount(); i++) {
            int k = i;
            enables.add(() -> {
                log.info("当前索引：{}", k);
                results.add((int) (Math.random() * 100));
                countDownLatch.countDown();
            });
        }
        log.info("提交线程");
        enables.forEach(runnable -> executor.submit(runnable));
        log.info("等待线程结束");
//        countDownLatch.await();
        log.info("结束线程池");
//        executor.shutdown();
        log.info("开启线程数线程数：{}", enables.size());
        log.info("结果数组长度：{}", results.size());
        log.info("处理用时(毫秒)：{}", System.currentTimeMillis() - start);
    }

    private static ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 核心线程数目
        executor.setCorePoolSize(12);
        // 指定最大线程数
        executor.setMaxPoolSize(256);
        // 队列中最大的数目
        executor.setQueueCapacity(1024);
        // 线程名称前缀
        executor.setThreadNamePrefix("test-thread-");
        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是由调用者所在的线程来执行
        // 对拒绝task的处理策略
        // executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 线程空闲后的最大存活时间
        executor.setKeepAliveSeconds(60);
        // 加载
        executor.initialize();
        return executor;
    }
}
