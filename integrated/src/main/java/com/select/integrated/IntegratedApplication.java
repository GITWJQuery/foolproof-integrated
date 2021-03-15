package com.select.integrated;

import com.select.integrated.config.ApplicationConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({ApplicationConfig.class})
public class IntegratedApplication {
    public static void main(String[] args) {
        SpringApplication.run(IntegratedApplication.class, args);
    }
}
