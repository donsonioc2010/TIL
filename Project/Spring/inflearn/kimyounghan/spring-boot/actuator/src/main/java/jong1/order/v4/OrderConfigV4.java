package jong1.order.v4;

import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.MeterRegistry;
import jong1.order.OrderService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderConfigV4 {
    @Bean
    public OrderService orderService() {
        return new OrderServiceV4();
    }

    // 해당 설정이 있어야 @Timed가 적용이 된다.
    @Bean
    public TimedAspect timedAspect(MeterRegistry registry) {
        return new TimedAspect(registry);
    }
}
