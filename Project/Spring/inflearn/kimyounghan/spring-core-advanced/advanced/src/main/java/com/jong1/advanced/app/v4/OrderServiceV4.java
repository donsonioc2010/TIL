package com.jong1.advanced.app.v4;

import com.jong1.advanced.trace.TraceStatus;
import com.jong1.advanced.trace.logtrace.LogTrace;
import com.jong1.advanced.trace.template.AbstarctTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceV4 {
    private final OrderRepositoryV4 orderRepository;
    private final LogTrace trace;

    public void orderItem(String itemId) {

        AbstarctTemplate<Void> template = new AbstarctTemplate<>(trace) {
            @Override
            protected Void call() {
                orderRepository.save( itemId);
                return null;
            }
        };
        template.execute("OrderService.orderItem()");
    }
}
