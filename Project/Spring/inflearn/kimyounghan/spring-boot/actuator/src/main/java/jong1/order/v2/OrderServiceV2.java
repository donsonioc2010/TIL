package jong1.order.v2;

import io.micrometer.core.annotation.Counted;
import java.util.concurrent.atomic.AtomicInteger;
import jong1.order.OrderService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OrderServiceV2 implements OrderService {
    private AtomicInteger stock = new AtomicInteger(100);

    @Counted("my.order")
    @Override
    public void order() {
        log.info("주문");
        stock.decrementAndGet();
    }

    @Counted("my.order") // value는 name을 주입하며 class에는 클래스명이 주입되며, method에는 메소드명이 주입된다.
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
