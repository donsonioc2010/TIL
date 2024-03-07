package jong1.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Slf4j
@Aspect
public class AspectV6Advice {

    // jong1.aop.order 패키지와 하위패키지 이면서, 클래스 이름이 *Service로 끝나는 것들
    @Around("jong1.aop.order.aop.Pointcuts.orderAndService()")
    public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            //@Before
            log.info("[트랜잭션 시작] {}", joinPoint.getSignature());
            Object result = joinPoint.proceed();

            // @AfterReturning
            log.info("[트랜잭션 종료] {}", joinPoint.getSignature());
            return result;
        }
        catch (Exception e) {
            // @AfterThrowing
            log.info("[트랜잭션 롤백] {}", joinPoint.getSignature());
            throw e;
        }
        finally {
            // @After
            log.info("[리소스 릴리즈] {}", joinPoint.getSignature());
        }
    }

    @Before("jong1.aop.order.aop.Pointcuts.orderAndService()")
    public void doBefore(JoinPoint joinPoint) { // JoinPoint만 사용이 가능함
        log.info("[Before] {}", joinPoint.getSignature());
    }

    // retuning의 value와 Argument의 파라미터명이 매칭되서 값이 주입된다
    @AfterReturning(value = "jong1.aop.order.aop.Pointcuts.orderAndService()", returning = "result")
    public void doAfterReturning(JoinPoint joinPoint, Object result) { // result를 조작은 불가하다
        log.info("[AfterReturning] {}, result >>> {}", joinPoint.getSignature(), result);
    }

    // throwing의 value와 Argument의 파라미터명이 매칭되서 값이 주입된다
    @AfterThrowing(value = "jong1.aop.order.aop.Pointcuts.orderAndService()", throwing = "ex")
    public void doAfterThrowing(JoinPoint joinPoint, Exception ex) {
        log.info("[AfterThrowing] {}, exception >>> {}", joinPoint.getSignature(), ex);
    }

    @After("jong1.aop.order.aop.Pointcuts.orderAndService()")
    public void doAfter(JoinPoint joinPoint) {
        log.info("[After] {}", joinPoint.getSignature());
    }
}
