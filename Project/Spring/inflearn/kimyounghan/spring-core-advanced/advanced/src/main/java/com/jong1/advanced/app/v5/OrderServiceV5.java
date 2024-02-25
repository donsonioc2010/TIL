package com.jong1.advanced.app.v5;

import com.jong1.advanced.trace.callback.TraceCallback;
import com.jong1.advanced.trace.callback.TraceTemplate;
import com.jong1.advanced.trace.logtrace.LogTrace;
import com.jong1.advanced.trace.template.AbstarctTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceV5 {

    private final OrderRepositoryV5 orderRepository;
    private final TraceTemplate traceTemplate;

    public OrderServiceV5(OrderRepositoryV5 orderRepository, LogTrace trace) {
        this.orderRepository = orderRepository;
        this.traceTemplate = new TraceTemplate(trace);
    }

    public void orderItem(String itemId) {
        traceTemplate.execute("OrderService.orderItem()", new TraceCallback<>() {
            @Override
            public Void call() {
                orderRepository.save(itemId);
                return null;
            }
        });
    }
}
