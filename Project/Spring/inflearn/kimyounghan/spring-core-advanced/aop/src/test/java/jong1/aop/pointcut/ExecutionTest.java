package jong1.aop.pointcut;

import java.lang.reflect.Method;
import jong1.aop.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

@Slf4j
public class ExecutionTest {

    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
    Method helloMethod;

    @BeforeEach
    public void init() throws NoSuchMethodException {
        helloMethod = MemberServiceImpl.class.getMethod("hello", String.class);
    }

    @Test
    void printMethod() {
        //public java.lang.String jong1.aop.member.MemberServiceImpl.hello(java.lang.String)
        log.info("helloMethod >>> {}", helloMethod);
    }

    @Test
    void exactMatch() {
        //public java.lang.String jong1.aop.member.MemberServiceImpl.hello(java.lang.String)
        pointcut.setExpression(
            "execution(public String jong1.aop.member.MemberServiceImpl.hello(String))");
        Assertions.assertTrue(pointcut.matches(helloMethod, MemberServiceImpl.class));
    }

    @Test
    void exactNotMatch() {
        //타입이 달라서
        //public java.lang.String jong1.aop.member.MemberServiceImpl.hello(java.lang.String)
        pointcut.setExpression(
            "execution(public Integer jong1.aop.member.MemberServiceImpl.hello(String))");
        Assertions.assertFalse(pointcut.matches(helloMethod, MemberServiceImpl.class));
    }

    @Test
    void allMatch() {
        // 모든접근제어자, 모든 메소드, 모든파라미터 타입, 갯수 상관없이 적용한다
        pointcut.setExpression("execution(* *(..))");
        Assertions.assertTrue(pointcut.matches(helloMethod, MemberServiceImpl.class));
    }

    @Test
    void nameMatch() {
        pointcut.setExpression("execution(* hello(..))");
        Assertions.assertTrue(pointcut.matches(helloMethod, MemberServiceImpl.class));
    }

    @Test
    void namePatternMatch1() {
        pointcut.setExpression("execution(* hel*(..))");
        Assertions.assertTrue(pointcut.matches(helloMethod, MemberServiceImpl.class));
    }

    @Test
    void namePatternMatch2() {
        pointcut.setExpression("execution(* *ll*(..))");
        Assertions.assertTrue(pointcut.matches(helloMethod, MemberServiceImpl.class));
    }

    @Test
    void nameMatchFalse() {
        pointcut.setExpression("execution(* nonononoo(..))");
        Assertions.assertFalse(pointcut.matches(helloMethod, MemberServiceImpl.class));
    }

    @Test
    void packageExactMatch1() {
        pointcut.setExpression("execution(* jong1.aop.member.MemberServiceImpl.hello(..))");
        Assertions.assertTrue(pointcut.matches(helloMethod, MemberServiceImpl.class));
    }

    @Test
    void packageExactMatch2() {
        pointcut.setExpression("execution(* jong1.aop.member.*.hello(..))");
        Assertions.assertTrue(pointcut.matches(helloMethod, MemberServiceImpl.class));
    }

    @Test
    void packageExactMatch3() {
        pointcut.setExpression("execution(* jong1.aop.member.*.*(..))");
        Assertions.assertTrue(pointcut.matches(helloMethod, MemberServiceImpl.class));
    }

    @Test
    void packageExactMatchFalse() {
        // jong1.aop 하위 클래스의 메소드들만 적용된다.
        pointcut.setExpression("execution(* jong1.aop.*.*(..))");
        Assertions.assertFalse(pointcut.matches(helloMethod, MemberServiceImpl.class));
    }

    @Test
    void packageMatchSubPackage1() {
        // jong1.aop 하위 클래스의 메소드들만 적용된다.
        pointcut.setExpression("execution(* jong1.aop..*.*(..))");
        Assertions.assertTrue(pointcut.matches(helloMethod, MemberServiceImpl.class));
    }


}