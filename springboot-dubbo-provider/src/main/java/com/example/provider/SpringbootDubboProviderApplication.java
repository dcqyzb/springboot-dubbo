package com.example.provider;

import com.example.dubbo.zipkin.annotation.EnableTraceAutoConfigurationProperties;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
@EnableTraceAutoConfigurationProperties
public class SpringbootDubboProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootDubboProviderApplication.class, args);
    }

}
