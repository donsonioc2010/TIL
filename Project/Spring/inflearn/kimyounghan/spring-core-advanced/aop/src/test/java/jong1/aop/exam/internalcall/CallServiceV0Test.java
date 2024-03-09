package jong1.aop.exam.internalcall;

import jong1.aop.exam.internalcall.aop.CallLogAspect;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@Import({CallLogAspect.class})
@SpringBootTest
class CallServiceV0Test {

    @Autowired
    private CallServiceV0 callServiceV0;


    // 내부메서드를 호출하는 경우 가장 먼저 호출하는 메소드만 AOP대상이 된다.
    @Test
    void external() {
        callServiceV0.external();
    }

    @Test
    void internal() {
        callServiceV0.internal();
    }
}