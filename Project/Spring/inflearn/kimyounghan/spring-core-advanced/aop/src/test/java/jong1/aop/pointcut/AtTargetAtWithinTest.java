package jong1.aop.pointcut;

import jong1.aop.member.annotation.ClassAop;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@Slf4j
@Import(AtTargetAtWithinTest.Config.class)
@SpringBootTest
public class AtTargetAtWithinTest {

    @Autowired
    private Child child;

    @Test
    void success() {
        log.info("child Proxy >>> {}", child.getClass());
        child.childMethod();
        child.parentMethod();
    }

    static class Config {
        @Bean
        public Parent parent() {
            return new Parent();
        }

        @Bean
        public Child child() {
            return new Child();
        }

        @Bean
        public AtTargetAtWithinAspect atTargetAtWithinAspect() {
            return new AtTargetAtWithinAspect();
        }
    }

    static class Parent {
        public void parentMethod() {} // 부모에만 존재하는 메서드
    }

    @ClassAop
    static class Child extends Parent {
        public void childMethod() {} // 자식에만 존재하는 메서드
    }

    @Slf4j
    @Aspect
    static class AtTargetAtWithinAspect {
        // @target : 인스턴스 기준으로 모든 메서드의 조인포인트를 선정, 부모타입의 메서드도 적용한다.
        @Around("execution(* jong1.aop..*(..)) && @target(jong1.aop.member.annotation.ClassAop)")
        public Object atTarget(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[@target] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }

        // @within : 선택된 클래스 내부에 있는 메서드만 조인 포인트로 선정한다. 부모타입의 메서드는 적용하지 않는다.
        @Around("execution(* jong1.aop..*(..)) && @within(jong1.aop.member.annotation.ClassAop)")
        public Object atWithin(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[@within] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }

    }
}
