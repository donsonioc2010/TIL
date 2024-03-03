package jong1.proxy.config.v1_proxy;

import jong1.proxy.app.v2.OrderControllerV2;
import jong1.proxy.app.v2.OrderRepositoryV2;
import jong1.proxy.app.v2.OrderServiceV2;
import jong1.proxy.config.v1_proxy.concrete_proxy.OrderControllerConcreteProxy;
import jong1.proxy.config.v1_proxy.concrete_proxy.OrderRepositoryConcreteProxy;
import jong1.proxy.config.v1_proxy.concrete_proxy.OrderServiceConcreteProxy;
import jong1.proxy.trace.logtrace.LogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConcreteProxyConfig {
    @Bean
    public OrderControllerV2 orderControllerV2(LogTrace logTrace) {
        OrderControllerV2 target = new OrderControllerV2(orderServiceV2(logTrace));
        return new OrderControllerConcreteProxy(target, logTrace);
    }

    @Bean
    public OrderServiceV2 orderServiceV2(LogTrace logTrace) {
        OrderServiceV2 target = new OrderServiceV2(orderRepositoryV2(logTrace));
        return new OrderServiceConcreteProxy(target, logTrace);
    }

    @Bean
    public OrderRepositoryV2 orderRepositoryV2(LogTrace logTrace) {
        OrderRepositoryV2 target = new OrderRepositoryV2();
        return new OrderRepositoryConcreteProxy(target, logTrace);
    }
}
