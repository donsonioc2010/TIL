package com.jong1.advanced.trace.template;

import com.jong1.advanced.trace.template.code.AbstractTemplate;
import com.jong1.advanced.trace.template.code.SubClassLogic1;
import com.jong1.advanced.trace.template.code.SubClassLogic2;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class TemplateMethodTest {

    @Test
    void templateMethodV0() {
        logic1();
        logic2();
    }

    private void logic1() {
        long startTime = System.currentTimeMillis();

        // 비즈니스 로직 실행
        log.info("비즈니스 로직1 실행");
        // 비즈니스 로직 종료

        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime >>> {}ms", resultTime);
    }

    private void logic2() {
        long startTime = System.currentTimeMillis();

        // 비즈니스 로직 실행
        log.info("비즈니스 로직2 실행");
        // 비즈니스 로직 종료

        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime >>> {}ms", resultTime);
    }

    /**
     * 템플릿 메소드 패턴 적용
     */
    @Test
    void templateMethodV1() {
        AbstractTemplate logic1 = new SubClassLogic1();
        logic1.execute();

        AbstractTemplate logic2 = new SubClassLogic2();
        logic2.execute();
    }

    /**
     * 익명 내부 클래스로 템플릿 메소드패턴 적용하기
     */
    @Test
    void templateMethodV2() {
        AbstractTemplate logic1 = new AbstractTemplate() {
            @Override
            protected void call() {
                log.info("비즈니스 로직 1 실행");
            }
        };

        AbstractTemplate logic2 = new AbstractTemplate() {
            @Override
            protected void call() {
                log.info("비즈니스 로직 2 실행");
            }
        };

        logic1.execute();
        logic2.execute();
    }

}
