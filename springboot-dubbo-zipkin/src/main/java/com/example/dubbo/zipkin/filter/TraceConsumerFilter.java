package com.example.dubbo.zipkin.filter;

import com.example.dubbo.zipkin.context.TraceContext;
import com.example.dubbo.zipkin.trace.TraceAgent;
import com.example.dubbo.zipkin.utils.IdUtils;
import com.example.dubbo.zipkin.utils.NetworkUtils;
import com.twitter.zipkin.gen.Annotation;
import com.twitter.zipkin.gen.Endpoint;
import com.twitter.zipkin.gen.Span;
import com.google.common.base.Stopwatch;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 消费端日志过滤器
 */
@Activate
public class TraceConsumerFilter implements Filter {

    private Span startTrace(Invoker<?> invoker, Invocation invocation) {

        Span consumerSpan = new Span();
        Long traceId = null;
        Long id = IdUtils.get();
        consumerSpan.setId(id);

        if (null == TraceContext.getTraceId()) {//如果是第一次连接就生成traceId
            TraceContext.start();
            traceId = id;
        } else {//否者直接获取traceID
            traceId = TraceContext.getTraceId();
        }

        consumerSpan.setTrace_id(traceId);
        consumerSpan.setParent_id(TraceContext.getSpanId());
        consumerSpan.setName(TraceContext.getTraceConfig().getApplicationName());
        Long timestamp = System.currentTimeMillis() * 1000;
        consumerSpan.setTimestamp(timestamp);

        Endpoint endpoint = Endpoint.create(
                TraceContext.getTraceConfig().getApplicationName(),
                NetworkUtils.ip2Num(NetworkUtils.getSiteIp()),
                TraceContext.getTraceConfig().getServerPort());
        Annotation annotation = Annotation.create(timestamp, TraceContext.ANNO_CS, endpoint);
        consumerSpan.addToAnnotations(annotation);

        Map<String, String> attaches = invocation.getAttachments();
        attaches.put(TraceContext.TRACE_ID_KEY, String.valueOf(consumerSpan.getParent_id()));
        attaches.put(TraceContext.SPAN_ID_KEY, String.valueOf(consumerSpan.getId()));
        return consumerSpan;
    }

    private void endTrace(Span span, Stopwatch watch) {
        Endpoint endpoint = Endpoint.create(
                span.getName(),
                NetworkUtils.ip2Num(NetworkUtils.getSiteIp()),
                TraceContext.getTraceConfig().getServerPort());
        Annotation annotation = Annotation.create(System.currentTimeMillis() * 1000, TraceContext.ANNO_CR, endpoint);
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
        Stopwatch watch = Stopwatch.createStarted();
        Span span = this.startTrace(invoker, invocation);
        TraceContext.start();
        TraceContext.setTraceId(span.getTrace_id());
        TraceContext.setSpanId(span.getId());
        TraceContext.addSpan(span);
        Result result = invoker.invoke(invocation);
        this.endTrace(span, watch);
        return result;
    }
}
