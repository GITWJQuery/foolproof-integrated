package com.select.integrated.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池
 */
@Configuration
public class QueryExecutorPool {

    /**
     * 线程池core数量，推荐与DB机器cpu核心总数相同
     */
    @Value("${query.thread.pool.core.size}")
    private Integer coreSize;

    @Bean(name = "queryExecutor")
    public ThreadPoolTaskExecutor queryExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 核心线程数目
        executor.setCorePoolSize(coreSize);
        // 指定最大线程数
        executor.setMaxPoolSize(128);
        // 队列中最大的数目
        executor.setQueueCapacity(1024);
        // 线程名称前缀
        executor.setThreadNamePrefix("query-thread-");
        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是由调用者所在的线程来执行
        // 对拒绝task的处理策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 线程空闲后的最大存活时间
        executor.setKeepAliveSeconds(60);
        // 加载
        executor.initialize();
        return executor;
    }
}
