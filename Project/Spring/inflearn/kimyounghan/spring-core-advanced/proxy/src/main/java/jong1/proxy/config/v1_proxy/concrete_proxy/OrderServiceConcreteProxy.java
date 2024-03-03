package jong1.proxy.config.v1_proxy.concrete_proxy;

import jong1.proxy.app.v1.OrderServiceV1;
import jong1.proxy.app.v2.OrderRepositoryV2;
import jong1.proxy.app.v2.OrderServiceV2;
import jong1.proxy.trace.TraceStatus;
import jong1.proxy.trace.logtrace.LogTrace;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OrderServiceConcreteProxy extends OrderServiceV2 {

    private final OrderServiceV2 target;
    private final LogTrace logTrace;

    public OrderServiceConcreteProxy(OrderServiceV2 target, LogTrace logTrace) {
        super(null);
        this.target = target;
        this.logTrace = logTrace;
    }

    @Override
    public void orderItem(String itemId) {
        log.info("OrderServiceInterfaceProxy.orderItem()");
        TraceStatus status = null;
        try {
            status = logTrace.begin("OrderService.orderItem()");
            target.orderItem(itemId);
            logTrace.end(status);
        } catch (Exception e) {
            logTrace.exception(status, e);
            throw e;
        }
    }
}
