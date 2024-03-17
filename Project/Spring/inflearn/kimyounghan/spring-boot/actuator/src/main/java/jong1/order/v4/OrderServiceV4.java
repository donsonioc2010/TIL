package jong1.order.v4;

import io.micrometer.core.annotation.Timed;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import jong1.order.OrderService;
import lombok.extern.slf4j.Slf4j;

@Timed(value="my.order")
@Slf4j
public class OrderServiceV4 implements OrderService {

    private AtomicInteger stock = new AtomicInteger(100);

    @Override
    public void order() {
        log.info("주문");
        stock.decrementAndGet();
        sleep(500);
    }

    @Override
    public void cancel() {
        log.info("취소");
        stock.incrementAndGet();
        sleep(300);
    }

    @Override
    public AtomicInteger getStock() {
        return stock;
    }

    private static void sleep(int millis) {
        try {
            Thread.sleep(millis + new Random().nextInt(200));
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
