package jong1.proxy.proxyfactory;

import jong1.proxy.common.advice.TimeAdvice;
import jong1.proxy.common.service.ConcreteService;
import jong1.proxy.common.service.ServiceImpl;
import jong1.proxy.common.service.ServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.AopUtils;

@Slf4j
public class ProxyFactoryTest {

    @Test
    @DisplayName("인터페이스가 있으면 JDK 동적 프록시 사용")
    void interfaceProxy() {
        ServiceInterface target = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.addAdvice(new TimeAdvice());
        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

        log.info("target.getClass() >>> {}", target.getClass());
        log.info("proxy.getClass() >>> {}", proxy.getClass());

        proxy.save();
        Assertions.assertTrue(AopUtils.isAopProxy(proxy));
        Assertions.assertTrue(AopUtils.isJdkDynamicProxy(proxy));
        Assertions.assertFalse(AopUtils.isCglibProxy(proxy));
    }

    @Test
    @DisplayName("인터페이스가 없으면 CGLIB 프록시 사용")
    void concreteProxy() {
        ConcreteService target = new ConcreteService();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.addAdvice(new TimeAdvice());
        ConcreteService proxy = (ConcreteService) proxyFactory.getProxy();

        log.info("target.getClass() >>> {}", target.getClass());
        log.info("proxy.getClass() >>> {}", proxy.getClass());

        proxy.call();

        Assertions.assertTrue(AopUtils.isAopProxy(proxy));
        Assertions.assertFalse(AopUtils.isJdkDynamicProxy(proxy));
        Assertions.assertTrue(AopUtils.isCglibProxy(proxy));
    }

    @Test
    @DisplayName("ProxyTargetClass 옵션을 사용시, 인터페이스가 있어도 CGLIB를 사용하고, 클래스 기반 프록시를 사용")
    void proxyTargetClass() {
        ServiceInterface target = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.setProxyTargetClass(true);
        proxyFactory.addAdvice(new TimeAdvice());
        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

        log.info("target.getClass() >>> {}", target.getClass());
        log.info("proxy.getClass() >>> {}", proxy.getClass());

        proxy.save();
        Assertions.assertTrue(AopUtils.isAopProxy(proxy));
        Assertions.assertFalse(AopUtils.isJdkDynamicProxy(proxy));
        Assertions.assertTrue(AopUtils.isCglibProxy(proxy));

    }
}
