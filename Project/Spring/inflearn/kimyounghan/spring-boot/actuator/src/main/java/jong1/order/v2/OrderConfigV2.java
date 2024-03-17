package jong1.order.v2;

import io.micrometer.core.aop.CountedAspect;
import io.micrometer.core.instrument.MeterRegistry;
import jong1.order.OrderService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderConfigV2 {
    @Bean
    public OrderService orderService() {
        return new OrderServiceV2();
    }

    // 해당 설정이 있어야 @Counted가 적용이 된다.
    @Bean
    public CountedAspect countedAspect(MeterRegistry registry) {
        return new CountedAspect(registry);
    }
}
