package com.example.customert;

import com.example.dubbo.zipkin.annotation.EnableTraceAutoConfigurationProperties;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
@EnableTraceAutoConfigurationProperties
public class DubboCustomertApplication {

    public static void main(String[] args) {
        SpringApplication.run(DubboCustomertApplication.class, args);
    }

}
