package com.example.dubbo.zipkin.trace.config;

import com.example.dubbo.zipkin.annotation.EnableTraceAutoConfigurationProperties;
import com.example.dubbo.zipkin.context.TraceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * 日志追踪自动配置开关
 */
@Configuration
@ConditionalOnBean(annotation = EnableTraceAutoConfigurationProperties.class)
@AutoConfigureAfter(SpringBootConfiguration.class)
@EnableConfigurationProperties(TraceConfig.class)
public class EnableTraceAutoConfiguration {
    @Autowired
    private TraceConfig traceConfig;

    @PostConstruct
    public void init() throws Exception {
        TraceContext.init(this.traceConfig);
    }
}
