package jong1.aop;

import jong1.aop.order.OrderRepository;
import jong1.aop.order.OrderService;
import jong1.aop.order.aop.AspectV1;
import jong1.aop.order.aop.AspectV2;
import jong1.aop.order.aop.AspectV3;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

//@Import(AspectV1.class)
//@Import(AspectV2.class)
@Import(AspectV3.class)
@Slf4j
@SpringBootTest
public class AopTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    void aopInfo() {
        log.info("isAopProxy, orderService >>> {}", AopUtils.isAopProxy(orderService));
        log.info("isAopProxy, orderRepository >>> {}", AopUtils.isAopProxy(orderRepository));
    }

    @Test
    void success() {
        orderService.orderItem("itemA");
    }
    @Test
    void exception() {
        Assertions.assertThrows(IllegalStateException.class, () -> orderService.orderItem("ex"));
    }
}
