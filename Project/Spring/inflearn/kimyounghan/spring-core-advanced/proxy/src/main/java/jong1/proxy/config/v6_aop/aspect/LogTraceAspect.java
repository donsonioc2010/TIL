package jong1.proxy.config.v6_aop.aspect;

import jong1.proxy.trace.TraceStatus;
import jong1.proxy.trace.logtrace.LogTrace;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Slf4j
@Aspect
public class LogTraceAspect {

    private final LogTrace logTrace;

    public LogTraceAspect(LogTrace logTrace) {
        this.logTrace = logTrace;
    }

    @Around("execution(* jong1.proxy.app..*(..))") //포인트컷 역할
    public Object execute(ProceedingJoinPoint jointPoint) throws Throwable {
        // 어드바이스 로직
        TraceStatus status = null;
        try {
            String message = jointPoint.getSignature().toShortString();
            status = logTrace.begin(message);
            Object result = jointPoint.proceed();
            logTrace.end(status);
            return result;
        } catch (Exception e) {
            logTrace.exception(status, e);
            throw e;
        }
    }
}
