package com.example.dubbo.zipkin.trace;

import com.example.dubbo.zipkin.context.TraceContext;
import com.example.dubbo.zipkin.trace.collector.HttpCollector;
import com.example.dubbo.zipkin.trace.collector.SimpleMetricsHandler;
import com.github.kristofa.brave.AbstractSpanCollector;
import com.github.kristofa.brave.SpanCollectorMetricsHandler;
import com.twitter.zipkin.gen.Span;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * 日志追踪代理器
 */
public class TraceAgent {

    private final AbstractSpanCollector collector;

    private final int THREAD_POOL_COUNT = 5;

    private final ExecutorService executor =
            Executors.newFixedThreadPool(this.THREAD_POOL_COUNT, new ThreadFactory() {
                @Override
                public Thread newThread(Runnable r) {
                    Thread worker = new Thread(r);
                    worker.setName("TRACE-AGENT-WORKER");
                    worker.setDaemon(true);
                    return worker;
                }
            });

    public TraceAgent(String server) {

        SpanCollectorMetricsHandler metrics = new SimpleMetricsHandler();

        collector = HttpCollector.create(server, TraceContext.getTraceConfig(), metrics);
    }

    public void send(final List<Span> spans) {
        if (spans != null && !spans.isEmpty()) {
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    for (Span span : spans) {
                        collector.collect(span);
                    }
                    collector.flush();
                }
            });
        }
    }
}
