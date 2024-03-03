package jong1.proxy.config.v1_proxy;

import jong1.proxy.app.v1.OrderControllerV1;
import jong1.proxy.app.v1.OrderControllerV1Impl;
import jong1.proxy.app.v1.OrderRepositoryV1;
import jong1.proxy.app.v1.OrderRepositoryV1Impl;
import jong1.proxy.app.v1.OrderServiceV1;
import jong1.proxy.app.v1.OrderServiceV1Impl;
import jong1.proxy.config.v1_proxy.interface_proxy.OrderControllerInterfaceProxy;
import jong1.proxy.config.v1_proxy.interface_proxy.OrderRepositoryInterfaceProxy;
import jong1.proxy.config.v1_proxy.interface_proxy.OrderServiceInterfaceProxy;
import jong1.proxy.trace.logtrace.LogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InterfaceProxyConfig {
    @Bean
    public OrderControllerV1 orderControllerV1(LogTrace logTrace) {
        OrderControllerV1 target = new OrderControllerV1Impl(orderServiceV1(logTrace));
        return new OrderControllerInterfaceProxy(target, logTrace);
    }

    @Bean
    public OrderServiceV1 orderServiceV1(LogTrace logTrace) {
        OrderServiceV1 target = new OrderServiceV1Impl(orderRepositoryV1(logTrace));
        return new OrderServiceInterfaceProxy(target, logTrace);
    }

    @Bean
    public OrderRepositoryV1 orderRepositoryV1(LogTrace logTrace) {
        OrderRepositoryV1 target = new OrderRepositoryV1Impl();
        return new OrderRepositoryInterfaceProxy(target, logTrace);
    }
}
