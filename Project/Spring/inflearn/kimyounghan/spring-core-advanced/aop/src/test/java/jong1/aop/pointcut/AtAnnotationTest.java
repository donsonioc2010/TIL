package jong1.aop.pointcut;

import jong1.aop.member.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Slf4j
@Import(AtAnnotationTest.AtAnnotationAspect.class)
@SpringBootTest
public class AtAnnotationTest {

    @Autowired
    private MemberService memberService;

    @Test
    void success() {
        log.info("memberService Proxy >>> {}", memberService.getClass());
        memberService.hello("helloA");
    }

    @Slf4j
    @Aspect
    static class AtAnnotationAspect {
        @Around("@annotation(jong1.aop.member.annotation.MethodAop)")
        public Object doAtAnnotation(ProceedingJoinPoint jp) throws Throwable {
            log.info("[@annotation] {}", jp.getSignature());
            return jp.proceed();
        }
    }
}
