package jong1.proxy.advisor;

import jong1.proxy.common.service.ServiceImpl;
import jong1.proxy.common.service.ServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;

public class MultiAdvisorTest {

    @Test
    @DisplayName("여러개의 프록시")
    void multiAdvisorTest1() {
        // client -> proxy2 -> proxy1 -> target

        // 프록시 1 생성
        ServiceInterface target1 = new ServiceImpl();
        ProxyFactory proxyFactory1 = new ProxyFactory(target1);
        DefaultPointcutAdvisor advisor1 = new DefaultPointcutAdvisor(Pointcut.TRUE, new Advice1());
        proxyFactory1.addAdvisor(advisor1);
        ServiceInterface proxy1 = (ServiceInterface) proxyFactory1.getProxy();

        // 프록시2
        ProxyFactory proxyFactory2 = new ProxyFactory(proxy1);
        DefaultPointcutAdvisor advisor2 = new DefaultPointcutAdvisor(Pointcut.TRUE, new Advice2());
        proxyFactory2.addAdvisor(advisor2);
        ServiceInterface proxy2 = (ServiceInterface) proxyFactory2.getProxy();

        proxy2.save();
        proxy2.find();

    }

    @Slf4j
    static class Advice1 implements MethodInterceptor {

        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            log.info("advice1 호출");
            return invocation.proceed();
        }
    }

    @Slf4j
    static class Advice2 implements MethodInterceptor {

        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            log.info("advice2 호출");
            return invocation.proceed();
        }
    }

    @Test
    @DisplayName("하나의 프록시, 여러 어드바이저")
    void multiAdvisorTest2() {
        // client -> proxy -> advisor2 -> advisor1 -> target

        DefaultPointcutAdvisor advisor1 = new DefaultPointcutAdvisor(Pointcut.TRUE, new Advice1());
        DefaultPointcutAdvisor advisor2 = new DefaultPointcutAdvisor(Pointcut.TRUE, new Advice2());

        // 프록시 1 생성
        ServiceInterface target1 = new ServiceImpl();
        ProxyFactory proxyFactory1 = new ProxyFactory(target1);

        //설정한 순서대로 실행하기 때문에, 2부터 실행해야 위의 순서처럼 실행이 된다.
        proxyFactory1.addAdvisor(advisor2);
        proxyFactory1.addAdvisor(advisor1);

        ServiceInterface proxy1 = (ServiceInterface) proxyFactory1.getProxy();

        proxy1.save();
        proxy1.find();
    }

}
