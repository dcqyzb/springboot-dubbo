package com.example.dubbo.zipkin.trace.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 日志追踪 自动配置的相关属性
 */
@ConfigurationProperties(prefix = "dubbo.trace")
public class TraceConfig {
    private boolean enabled = true;

    private int connectTimeout;

    private int readTimeout;

    private int flushInterval = 0;

    private boolean compressionEnabled = true;

    private String zipkinUrl;

    @Value("${server.port}")
    private int serverPort;

    @Value("${spring.application.name}")
    private String applicationName;

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public int getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }

    public int getFlushInterval() {
        return flushInterval;
    }

    public void setFlushInterval(int flushInterval) {
        this.flushInterval = flushInterval;
    }

    public boolean isCompressionEnabled() {
        return compressionEnabled;
    }

    public void setCompressionEnabled(boolean compressionEnabled) {
        this.compressionEnabled = compressionEnabled;
    }

    public String getZipkinUrl() {
        return zipkinUrl;
    }

    public void setZipkinUrl(String zipkinUrl) {
        this.zipkinUrl = zipkinUrl;
    }

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }
}
