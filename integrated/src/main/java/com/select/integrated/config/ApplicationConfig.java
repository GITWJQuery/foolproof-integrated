package com.select.integrated.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * 应用配置类。
 */
@ComponentScan({"com.select.integrated.domain.service"})
@MapperScan("com.select.integrated.domain.mapper")
@EnableScheduling
public class ApplicationConfig {

}
