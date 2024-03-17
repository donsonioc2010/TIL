package jong1.order.guage;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.annotation.PostConstruct;
import jong1.order.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class StockConfigV1 {

    @Bean
    public MyStockMetric myStockMetric(OrderService orderService, MeterRegistry meterRegistry) {
        return new MyStockMetric(orderService, meterRegistry);
    }

    // 메트릭을 확인할 때마다 호출된다. (프로메테우스가 메트릭을 호출할떄나, Actuator로 직접 호출할떄나)
    @Slf4j
    static class MyStockMetric {

        private OrderService orderService;

        private MeterRegistry meterRegistry;

        public MyStockMetric(OrderService orderService, MeterRegistry meterRegistry) {
            this.orderService = orderService;
            this.meterRegistry = meterRegistry;
        }

        @PostConstruct
        public void init() {
            Gauge.builder("my.stock", orderService, service -> {
                    log.info("stock gauge call");
                    int stock = service.getStock().get();
                    return stock;
                })
                .description("제품 재고 수량")
                .register(meterRegistry);
        }
    }
}
