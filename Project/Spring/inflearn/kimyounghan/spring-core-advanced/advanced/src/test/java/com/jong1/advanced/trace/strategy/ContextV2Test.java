package com.jong1.advanced.trace.strategy;

import com.jong1.advanced.trace.strategy.code.ContextV1;
import com.jong1.advanced.trace.strategy.code.ContextV2;
import com.jong1.advanced.trace.strategy.code.StrategyLogic1;
import com.jong1.advanced.trace.strategy.code.StrategyLogic2;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class ContextV2Test {

    /**
     * 전략패턴을 파라미터로 전달받아 사용하는 방식
     */
    @Test
    void strategyV1() {
        ContextV2 contextV2 = new ContextV2();
        contextV2.execute(new StrategyLogic1());
        contextV2.execute(new StrategyLogic2());
    }

    @Test
    void strategyV2() {
        new ContextV2().execute(new StrategyLogic1());
        new ContextV2().execute(new StrategyLogic2());
    }

    @Test
    void strategyV3() {
        new ContextV2().execute(() -> log.info("비즈니스 로직1 실행"));
        new ContextV2().execute(() -> log.info("비즈니스 로직2 실행"));
    }

}
