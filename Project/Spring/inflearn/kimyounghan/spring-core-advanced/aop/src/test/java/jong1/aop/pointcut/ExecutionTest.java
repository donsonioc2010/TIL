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
        // jong1.aop.모든클래스.모든메소드 하위 클래스의 메소드들만 적용된다.
        pointcut.setExpression("execution(* jong1.aop.*.*(..))");
        Assertions.assertFalse(pointcut.matches(helloMethod, MemberServiceImpl.class));
    }

    @Test
    void packageMatchSubPackage1() {
        // jong1.aop.모든패키지.모든클래스.모든메소드 하위 클래스의 메소드들만 적용된다.
        pointcut.setExpression("execution(* jong1.aop..*.*(..))");
        Assertions.assertTrue(pointcut.matches(helloMethod, MemberServiceImpl.class));
    }

    @Test
    void typeMatchSuperType() {
        // 부모타입이어도 자식타입에 적용된다, 부모타입에 있는 것들만 허용됨.
        pointcut.setExpression("execution(* jong1.aop.member.MemberService.*(..))");
        Assertions.assertTrue(pointcut.matches(helloMethod, MemberServiceImpl.class));
    }

    @Test
    void typeMatchInternalNoSuperTypeMethodFalse() throws NoSuchMethodException {
        // 부모타입이어도 자식타입에 적용된다, 부모타입에 선언한 메소드들 까지만 허용됨.
        pointcut.setExpression("execution(* jong1.aop.member.MemberService.*(..))");
        Method internalMethod = MemberServiceImpl.class.getMethod("internal", String.class);
        Assertions.assertFalse(pointcut.matches(internalMethod, MemberServiceImpl.class));
    }

    @Test
    void typeMatchInternal() throws NoSuchMethodException {
        pointcut.setExpression("execution(* jong1.aop.member.MemberServiceImpl.*(..))");
        Method internalMethod = MemberServiceImpl.class.getMethod("internal", String.class);
        Assertions.assertTrue(pointcut.matches(internalMethod, MemberServiceImpl.class));
    }

    @Test
    void argsMatch() {
        pointcut.setExpression("execution(* *(String))");
        Assertions.assertTrue(pointcut.matches(helloMethod, MemberServiceImpl.class));
    }

    @Test
    void argsMatchNoArgs() {
        pointcut.setExpression("execution(* *())");
        Assertions.assertFalse(pointcut.matches(helloMethod, MemberServiceImpl.class));
    }

    @Test
    void argsMatchStar() {
        // 파라미터 갯수, 타입 상관없이 모두 허용
        pointcut.setExpression("execution(* *(..))");
        Assertions.assertTrue(pointcut.matches(helloMethod, MemberServiceImpl.class));
    }
    @Test
    void argsMatchComplex() {
        // String으로 시작해서, 모든 타입, 갯수 허용 (String), (String, Args 1), (String, Args1, Args2)emdemd
        pointcut.setExpression("execution(* *(String, ..))");
        Assertions.assertTrue(pointcut.matches(helloMethod, MemberServiceImpl.class));
    }



}