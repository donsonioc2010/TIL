package jong1.aop.proxyvs;

import jong1.aop.member.MemberService;
import jong1.aop.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;

@Slf4j
public class ProxyCastingTest {

    @Test
    void jdkProxy() {
        MemberServiceImpl target = new MemberServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.setProxyTargetClass(false); // JDK 동적 프록시로 생성

        // 프록시를 인터페이스로 캐스팅 성공케이스
        MemberService memberServiceProxy = (MemberService) proxyFactory.getProxy();


        // JDK 동적프록시는 MemberService를 자체적으로 구현한 것이기에 MemberServiceImpl에 대해서는 인지도 못하기 떄문에 캐스팅이 아얘 불가능하다.
        Assertions.assertThrows(ClassCastException.class, () -> {
            MemberServiceImpl castingMemberService = (MemberServiceImpl) memberServiceProxy;
        });
    }

    @Test
    void cglibProxy() {
        MemberServiceImpl target = new MemberServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.setProxyTargetClass(true);

        // 프록시를 인터페이스로 캐스팅 성공케이스
        MemberService memberServiceProxy = (MemberService) proxyFactory.getProxy();

        // CGLIB프록시는 타겟클래스(MemberServiceImpl)을 기반으로 프록시를 생성하기 때문에, 부모 클래스, 타겟클래스 모두 캐스팅이 가능하다.
        MemberServiceImpl castingMemberService = (MemberServiceImpl) memberServiceProxy;
    }
}
