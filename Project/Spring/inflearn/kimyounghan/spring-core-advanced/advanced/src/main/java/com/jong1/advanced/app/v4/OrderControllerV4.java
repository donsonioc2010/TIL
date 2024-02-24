package com.jong1.advanced.app.v4;

import com.jong1.advanced.trace.TraceStatus;
import com.jong1.advanced.trace.logtrace.LogTrace;
import com.jong1.advanced.trace.template.AbstarctTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderControllerV4 {
    private final OrderServiceV4 orderService;
    private final LogTrace trace;


    @GetMapping("/v4/request")
    public String request(String itemId) {

        AbstarctTemplate<String> template = new AbstarctTemplate<>(trace) {
            @Override
            protected String call() {
                orderService.orderItem(itemId);
                return "ok";
            }
        };

        return template.execute("OrderControllerV4.request()");
    }
}
