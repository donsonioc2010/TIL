package jong1.order.v0;

import java.util.concurrent.atomic.AtomicInteger;
import jong1.order.OrderService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OrderServiceV0 implements OrderService {

    private AtomicInteger stock = new AtomicInteger(100);

    @Override
    public void order() {
        log.info("주문");
        stock.decrementAndGet();
    }

    @Override
    public void cancel() {
        log.info("취소");
        stock.incrementAndGet();
    }

    @Override
    public AtomicInteger getStock() {
        return stock;
    }
}
