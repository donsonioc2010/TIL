package com.jong1.springtx.apply;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Slf4j
@SpringBootTest
public class InternalCallV1Test {

    @Autowired
    private CallService callService;

    @Test
    void callExternal() {
        // Internal이 Transaction이 없는결과가 나와버림
        callService.external();
    }
    @Test
    void callInternalReadOnly() {
        // 외부가 ReadOnly=true기 떄문에 둘다 true가 떠야함.정상!
        callService.internalReadOnly();
    }

    @TestConfiguration
    static class TestConfig {

        @Bean
        public CallService callService() {
            return new CallService();
        }
    }

    @Slf4j
    static class CallService {

        // 외부에서 호출하는 메소드
        public void external() {
            log.info("Call External");
            this.printTxInfo("external");
            this.internal();
        }

        @Transactional(readOnly = false)
        public void internal() {
            log.info("Call Internal");
            this.printTxInfo("internal");
        }

        @Transactional(readOnly = true)
        public void internalReadOnly(){
            log.info("Call Internal ReadOnly");
            this.printTxInfo("internalReadOnly");
            this.internal();
        }

        private void printTxInfo(String callMethod) {
            boolean txActive = TransactionSynchronizationManager.isActualTransactionActive();
            log.info("[{}]tx active >>> {}", callMethod, txActive);
            boolean readOnly = TransactionSynchronizationManager.isCurrentTransactionReadOnly();
            log.info("[{}]tx readOnly >>> {}", callMethod, readOnly);
        }
    }
}
