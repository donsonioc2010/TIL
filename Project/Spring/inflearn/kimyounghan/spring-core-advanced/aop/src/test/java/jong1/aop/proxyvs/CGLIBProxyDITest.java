package jong1.aop.proxyvs;

import jong1.aop.member.MemberService;
import jong1.aop.member.MemberServiceImpl;
import jong1.aop.proxyvs.code.ProxyDIAspect;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Slf4j
@Import(ProxyDIAspect.class)
@SpringBootTest(properties = {
    "spring.main.allow-circular-references=true",
    "spring.aop.proxy-target-class=true" // CGLIB
})
public class CGLIBProxyDITest {
    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberServiceImpl memberServiceImpl; // MemberService가 JDK프록시로 구현된거라 캐스팅이 불가능하여 주입이 안되면서 오류가

    @Test
    void go() {
        log.info("memberService class >>> {}", memberService.getClass());
        log.info("memberServiceImpl class >>> {}", memberServiceImpl.getClass());

        memberServiceImpl.hello("hello");
    }
}
