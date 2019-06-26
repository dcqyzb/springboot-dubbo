package com.example.dubbo.zipkin.filter;

import com.example.dubbo.zipkin.context.TraceContext;
import com.example.dubbo.zipkin.trace.TraceAgent;
import com.example.dubbo.zipkin.utils.IdUtils;
import com.example.dubbo.zipkin.utils.NetworkUtils;
import com.google.common.base.Stopwatch;
import com.twitter.zipkin.gen.Annotation;
import com.twitter.zipkin.gen.Endpoint;
import com.twitter.zipkin.gen.Span;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 服务端日志过滤器
 */
@Activate
public class TraceProviderFilter implements Filter {

    private Span startTrace(Map<String, String> attaches) {

        Long traceId = Long.valueOf(attaches.get(TraceContext.TRACE_ID_KEY));
        Long parentSpanId = Long.valueOf(attaches.get(TraceContext.SPAN_ID_KEY));

        TraceContext.start();
        TraceContext.setTraceId(traceId);
        TraceContext.setSpanId(parentSpanId);

        Span providerSpan = new Span();

        long id = IdUtils.get();
        providerSpan.setId(id);
        providerSpan.setParent_id(parentSpanId);
        providerSpan.setTrace_id(traceId);
        providerSpan.setName(TraceContext.getTraceConfig().getApplicationName());
        long timestamp = System.currentTimeMillis() * 1000;
        providerSpan.setTimestamp(timestamp);

        Endpoint endpoint = Endpoint.create(
                TraceContext.getTraceConfig().getApplicationName(),
                NetworkUtils.ip2Num(NetworkUtils.getSiteIp()),
                TraceContext.getTraceConfig().getServerPort());
        Annotation annotation = Annotation.create(timestamp, TraceContext.ANNO_SR, endpoint);
        providerSpan.addToAnnotations(annotation);

        TraceContext.addSpan(providerSpan);
        return providerSpan;
    }

    private void endTrace(Span span, Stopwatch watch) {
        Endpoint endpoint = Endpoint.create(
                span.getName(),
                NetworkUtils.ip2Num(NetworkUtils.getSiteIp()),
                TraceContext.getTraceConfig().getServerPort());
        Annotation annotation = Annotation.create(System.currentTimeMillis() * 1000, TraceContext.ANNO_SS, endpoint);
        span.addToAnnotations(annotation);
        span.setDuration(watch.stop().elapsed(TimeUnit.MICROSECONDS));
        TraceAgent traceAgent = new TraceAgent(TraceContext.getTraceConfig().getZipkinUrl());
        traceAgent.send(TraceContext.getSpans());
    }

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        if (!TraceContext.getTraceConfig().isEnabled()) {
            return invoker.invoke(invocation);
        }
        Map<String, String> attaches = invocation.getAttachments();
        if (!attaches.containsKey(TraceContext.TRACE_ID_KEY)) {
            return invoker.invoke(invocation);
        }
        Stopwatch watch = Stopwatch.createStarted();
        Span providerSpan = this.startTrace(attaches);
        Result result = invoker.invoke(invocation);
        this.endTrace(providerSpan, watch);
        return result;
    }

}
