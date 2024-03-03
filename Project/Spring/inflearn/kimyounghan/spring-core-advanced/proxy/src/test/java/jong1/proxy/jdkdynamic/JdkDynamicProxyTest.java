package jong1.proxy.jdkdynamic;

import java.lang.reflect.Proxy;
import jong1.proxy.jdkdynamic.code.AImpl;
import jong1.proxy.jdkdynamic.code.AInterface;
import jong1.proxy.jdkdynamic.code.BImpl;
import jong1.proxy.jdkdynamic.code.BInterface;
import jong1.proxy.jdkdynamic.code.TimeInvocationHandler;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class JdkDynamicProxyTest {

    @Test
    void dynamicA() {
        AInterface target = new AImpl();
        TimeInvocationHandler handler = new TimeInvocationHandler(target);

        AInterface proxy = (AInterface) Proxy.newProxyInstance(AInterface.class.getClassLoader(), new Class[] {AInterface.class}, handler);
        proxy.call();
        log.info("targetClass >>> {}", target.getClass());
        log.info("proxyClass >>> {}", proxy.getClass());
    }

    @Test
    void dynamicB() {
        BInterface target = new BImpl();
        TimeInvocationHandler handler = new TimeInvocationHandler(target);

        BInterface proxy = (BInterface) Proxy.newProxyInstance(BInterface.class.getClassLoader(), new Class[] {BInterface.class}, handler);
        log.info("B Interface Call Result >>> {}", proxy.call());
        log.info("targetClass >>> {}", target.getClass());
        log.info("proxyClass >>> {}", proxy.getClass());
    }
}
