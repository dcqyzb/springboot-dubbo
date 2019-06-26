package com.example.dubbo.zipkin.annotation;

import java.lang.annotation.*;

/**
 * 日志追踪是否启用的注解
 * dc
 * 2019/06/26
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EnableTraceAutoConfigurationProperties {

}
