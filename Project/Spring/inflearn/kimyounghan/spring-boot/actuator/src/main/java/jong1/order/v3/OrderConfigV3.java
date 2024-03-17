package jong1.order.v3;

import io.micrometer.core.instrument.MeterRegistry;
import jong1.order.OrderService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderConfigV3 {
    @Bean
    public OrderService orderService(MeterRegistry registry) {
        return new OrderServiceV3(registry);
    }
}
