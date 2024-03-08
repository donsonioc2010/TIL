package jong1.aop.pointcut;

import java.lang.reflect.Method;
import jong1.aop.member.MemberServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

public class WithinTest {

    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
    Method helloMethod;

    @BeforeEach
    public void init() throws NoSuchMethodException {
        helloMethod = MemberServiceImpl.class.getMethod("hello", String.class);
    }

    // 일치된 타입의 하위메소드를 모두 적용한다.
    @Test
    void withinExact() {
        pointcut.setExpression("within(jong1.aop.member.MemberServiceImpl)");
        Assertions.assertTrue(pointcut.matches(helloMethod, MemberServiceImpl.class));
    }

    @Test
    void withinStar() {
        pointcut.setExpression("within(jong1.aop.member.*Service*)");
        Assertions.assertTrue(pointcut.matches(helloMethod, MemberServiceImpl.class));
    }

    @Test
    void withinSubPackage() {
        pointcut.setExpression("within(jong1.aop..*)");
        Assertions.assertTrue(pointcut.matches(helloMethod, MemberServiceImpl.class));
    }

    @Test
    void withinSuperTypeFalse() {
        //타겟의 타입만 적용이 가능하며, 인터페이스는 지정을 해도 불가능하다.
        //SuperType은 execution에서 적용해야한다.
        pointcut.setExpression("within(jong1.aop.member.MemberService)");
        Assertions.assertFalse(pointcut.matches(helloMethod, MemberServiceImpl.class));
    }
}
