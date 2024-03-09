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
class CallServiceV3Test {

    @Autowired
    private CallServiceV3 callService;


    @Test
    void external() {
        callService.external();
    }

}