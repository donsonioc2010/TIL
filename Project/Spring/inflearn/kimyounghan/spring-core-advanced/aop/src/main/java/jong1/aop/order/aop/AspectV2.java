package jong1.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Slf4j
@Aspect
public class AspectV2 {

    // Pointcut Signature, 파라미터가 존재하는 경우 파라미터도 맞춰줘야함.
    // 장점은 다른 Aop에서 재사용도 가능해짐...! 퍼블릭등등 다 가능함
    @Pointcut("execution(* jong1.aop.order..*(..))")
    private void allOrder() {}

    @Around("allOrder()")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("[Log] {}", joinPoint.getSignature());
        return joinPoint.proceed();
    }
}
