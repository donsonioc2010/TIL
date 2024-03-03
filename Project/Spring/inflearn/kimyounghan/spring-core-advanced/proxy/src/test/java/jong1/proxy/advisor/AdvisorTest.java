package jong1.proxy.advisor;

import java.lang.reflect.Method;
import jong1.proxy.common.advice.TimeAdvice;
import jong1.proxy.common.service.ServiceImpl;
import jong1.proxy.common.service.ServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;

public class AdvisorTest {

    @Test
    void advisorTest1() {
        ServiceInterface target = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);

        //항상 참인 포인트컷을 설정하여 어드바이저를 생성
        // DefaultPointCutAdvisor 는 한개의 포인트컷과, 한개의 어드바이스가 필수이다.
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(Pointcut.TRUE, new TimeAdvice());
        proxyFactory.addAdvisor(advisor);

        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

        proxy.save();
        proxy.find();
    }

    @Test
    @DisplayName("직접 제작한 포인트컷")
    void advisorTest2() {
        ServiceInterface target = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);

        //항상 참인 포인트컷을 설정하여 어드바이저를 생성
        // DefaultPointCutAdvisor 는 한개의 포인트컷과, 한개의 어드바이스가 필수이다.
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(new MyPointCut(), new TimeAdvice());
        proxyFactory.addAdvisor(advisor);

        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

        proxy.save();
        proxy.find();
    }

    static class MyPointCut implements Pointcut {

        // 매번 성공하도록 설정
        @Override
        public ClassFilter getClassFilter() {
            return ClassFilter.TRUE;
        }

        @Override
        public MethodMatcher getMethodMatcher() {
            return new MyMethodMatcher();
        }
    }

    @Slf4j
    static class MyMethodMatcher implements MethodMatcher {

        private String matchName = "save";

        @Override
        public boolean matches(Method method, Class<?> targetClass) {
            boolean result = matchName.equals(method.getName());
            log.info("포인트컷 MethodMatcher 호출 >>> {}, targetClass >>> {}", method.getName(), targetClass);
            log.info("포인트컷 MethodMatcher 결과 >>> {}", result);
            return result;
        }

        @Override
        public boolean isRuntime() {
            // false인 경우에는 matches(Method, Class)를 호출하고, true인 경우에는 matches(Method, Class, Object[])를 호출한다.
            return false;
        }

        @Override
        public boolean matches(Method method, Class<?> targetClass, Object... args) {
            return false;
        }
    }


    @Test
    @DisplayName("스프링이 제공하는 포인트컷")
    void advisorTest3() {
        ServiceInterface target = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);

        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedNames("save", "save*");

        //항상 참인 포인트컷을 설정하여 어드바이저를 생성
        // DefaultPointCutAdvisor 는 한개의 포인트컷과, 한개의 어드바이스가 필수이다.
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(pointcut, new TimeAdvice());
        proxyFactory.addAdvisor(advisor);

        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

        proxy.save();
        proxy.find();
    }

}
