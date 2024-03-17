package jong1.order.v1;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import java.util.concurrent.atomic.AtomicInteger;
import jong1.order.OrderService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OrderServiceV1 implements OrderService {
    private final MeterRegistry registry;

    private AtomicInteger stock = new AtomicInteger(100);

    public OrderServiceV1(MeterRegistry registry) {
        this.registry = registry;
    }

    @Override
    public void order() {
        log.info("주문");
        stock.decrementAndGet();

        Counter.builder("my.order")
            .tag("class", this.getClass().getName())
            .tag("method", "order")
            .description("order")
            .register(registry)
            .increment(); // 이러면 카운터가 1이 증가한다.
    }

    @Override
    public void cancel() {
        log.info("취소");
        stock.incrementAndGet();

        Counter.builder("my.order")
            .tag("class", this.getClass().getName())
            .tag("method", "cancel")
            .description("cancel")
            .register(registry)
            .increment();
    }

    @Override
    public AtomicInteger getStock() {

        return stock;
    }
}
