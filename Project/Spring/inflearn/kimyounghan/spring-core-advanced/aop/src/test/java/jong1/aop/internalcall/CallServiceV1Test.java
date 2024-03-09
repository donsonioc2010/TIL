package jong1.aop.internalcall;

import jong1.aop.internalcall.aop.CallLogAspect;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Slf4j
@Import({CallLogAspect.class})
@SpringBootTest(properties = "spring.main.allow-circular-references=true") //스프링 2.6이후 순환참조를 금지하여, 허용을 하고싶은 경우 설정추가가 필요함.
class CallServiceV1Test {

    @Autowired
    private CallServiceV1 callService;

    /**
     * 내부에서 프록시로 생성된 프록시 객체를 주입받아 프록시메소드를 호출하면 해결이 가능하다
      */

    @Test
    void external() {
        callService.external();
    }

    @Test
    void internal() {
        callService.internal();
    }
}