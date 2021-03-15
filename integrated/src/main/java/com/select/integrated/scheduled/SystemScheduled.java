package com.select.integrated.scheduled;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时任务
 */
@Lazy(false)
@Component
@Configuration
@Slf4j
public class SystemScheduled {

    @Scheduled(cron = "0 0 */3 * * ?")
    public void execScheduled(){
        log.info("SystemScheduled->execScheduled: 定时任务开始执行");
    }
}
