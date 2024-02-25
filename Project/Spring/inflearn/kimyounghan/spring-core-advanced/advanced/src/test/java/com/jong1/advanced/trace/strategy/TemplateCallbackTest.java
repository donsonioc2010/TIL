package com.jong1.advanced.trace.strategy;

import com.jong1.advanced.trace.strategy.code.template.Callback;
import com.jong1.advanced.trace.strategy.code.template.TimeLogTemplate;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class TemplateCallbackTest {
    /**
     * 템플릿 콜백 패턴
     */
    @Test
    void callbackV1() {
        TimeLogTemplate template = new TimeLogTemplate();
        template.execute(() -> log.info("비즈니스 로직1 실행"));

        Callback logic2 = new Callback() {
            @Override
            public void call() {
                log.info("비즈니스 로직2 실행");
            }
        };
        template.execute(logic2);
    }

}
