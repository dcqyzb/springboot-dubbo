package com.example.dubbo.zipkin.context;

/**
 * 上下文基类
 * dc
 * 2019/06/26
 */
public abstract class AbstractContext {

    private String applicationName;

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }
}
