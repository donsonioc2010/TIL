package jong1.aop.pointcut;

import jong1.aop.member.MemberService;
import jong1.aop.member.annotation.ClassAop;
import jong1.aop.member.annotation.MethodAop;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Slf4j
@Import(ParameterTest.ParameterAspect.class)
@SpringBootTest
public class ParameterTest {
    @Autowired
    private MemberService memberService;

    @Test
    void success() {
        log.info("MemberService Proxy >>> {}", memberService.getClass());
        memberService.hello("helloA");
    }

    @Slf4j
    @Aspect
    static class ParameterAspect {
        @Pointcut("execution(* jong1.aop.member..*.*(..))")
        private void allMember() {}

        @Around("allMember()")
        public Object logArgs1(ProceedingJoinPoint jp) throws Throwable {
            Object arg1 = jp.getArgs()[0];
            log.info("[logArgs1] {}, arg1 >>> {}", jp.getSignature(), arg1);
            return jp.proceed();
        }

        @Around("allMember() && args(arg, ..)")
        public Object logArgs2(ProceedingJoinPoint jp, Object arg) throws Throwable {
            log.info("[logArgs2] {}, arg >>> {}", jp.getSignature(), arg);
            return jp.proceed();
        }

        @Before("allMember() && args(arg, ..)")
        public void logArgs3(String arg) { // Around로 써도 무방함. ProceddingJoinPoint, String
            log.info("[logArgs3] arg >>> {}", arg);
        }

        @Before("allMember() && this(obj)") // this는 프록시로 생성된 가짜 객체를 주입한다
        public void thisArgs(JoinPoint jp, MemberService obj) { // Around로 써도 무방하며, jp역시 사용하지 않아도 됨.
            log.info("[this] {}, obj >>> {}", jp.getSignature(), obj.getClass());
        }

        @Before("allMember() && target(obj)") // target은 실제 호출되는 객체를 주입한다
        public void targetArgs(JoinPoint jp, MemberService obj) { // Around로 써도 무방하며, jp역시 사용하지 않아도 됨.
            log.info("[target] {}, obj >>> {}", jp.getSignature(), obj.getClass());
        }

        @Before("allMember() && @target(annotation)")
        public void atTarget(JoinPoint jp, ClassAop annotation) { // Around로 써도 무방하며, jp역시 사용하지 않아도 됨.
            log.info("[@target] {}, obj >>> {}", jp.getSignature(), annotation);
        }

        @Before("allMember() && @within(annotation)")
        public void atWithin(JoinPoint jp, ClassAop annotation) { // Around로 써도 무방하며, jp역시 사용하지 않아도 됨.
            log.info("[@within] {}, obj >>> {}", jp.getSignature(), annotation);
        }

        @Before("allMember() && @annotation(annotation)")
        public void atAnnotation(JoinPoint jp, MethodAop annotation) { // Around로 써도 무방하며, jp역시 사용하지 않아도 됨.
            log.info("[@Annotation] {}, obj >>> {}, annotationValue >>> {}", jp.getSignature(), annotation, annotation.value());
        }
    }
}
