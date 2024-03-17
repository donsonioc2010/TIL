package jong1.order.guage;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.binder.MeterBinder;
import jong1.order.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Slf4j
@Configuration
public class StockConfigV2 {

    @Bean
    public MeterBinder stockSize(OrderService orderService) {
        return registry -> Gauge.builder("my.stock", orderService, service -> {
                log.info("stock gauge call");
                int stock = service.getStock().get();
                return stock;
            })
            .description("제품 재고 수량")
            .register(registry);
    }
}
