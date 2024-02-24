package com.jong1.advanced.app.v4;

import com.jong1.advanced.trace.TraceStatus;
import com.jong1.advanced.trace.logtrace.LogTrace;
import com.jong1.advanced.trace.template.AbstarctTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryV4 {
    private final LogTrace trace;

    public void save(String itemId) {
        AbstarctTemplate<Void> template = new AbstarctTemplate<>(trace) {
            @Override
            protected Void call() {

                if (itemId.equals("ex")) {
                    throw new IllegalArgumentException("예외 발생");
                }
                sleep(1000);
                return null;
            }
        };

        template.execute("OrderRepository.save()");
    }

    private void sleep(int mills) {
        try {
            Thread.sleep(mills);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
