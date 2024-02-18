package com.jong1.springtx.apply;

import lombok.RequiredArgsConstructor;
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
public class InternalCallV2Test {

    @Autowired
    private CallService callService;

    @Test
    void callExternalV2() {
        // Internal이 Transaction이 없는결과가 나와버림
        callService.external();
    }

    @Test
    void callExternalReadOnlyV2() {
        callService.externalReadOnly();
    }

    @TestConfiguration
    static class TestConfig {

        @Bean
        public CallService callService() {
            return new CallService(internalService());
        }

        @Bean
        public InternalService internalService() {
            return new InternalService();
        }
    }

    @Slf4j
    @RequiredArgsConstructor
    static class CallService {

        private final InternalService internalService;

        // 외부에서 호출하는 메소드
        public void external() {
            log.info("Call External");
            this.printTxInfo("external");
            internalService.internal();
        }

        @Transactional(readOnly = true)
        public void externalReadOnly() {
            log.info("Call External ReadOnly");
            this.printTxInfo("externalReadOnly");
            internalService.internal();
        }

        private void printTxInfo(String callMethod) {
            boolean txActive = TransactionSynchronizationManager.isActualTransactionActive();
            log.info("[{}]tx active >>> {}", callMethod, txActive);
            boolean readOnly = TransactionSynchronizationManager.isCurrentTransactionReadOnly();
            log.info("[{}]tx readOnly >>> {}", callMethod, readOnly);
        }
    }

    static class InternalService {

        @Transactional(readOnly = false)
        public void internal() {
            log.info("Call Internal");
            this.printTxInfo("internal");
        }

        private void printTxInfo(String callMethod) {
            boolean txActive = TransactionSynchronizationManager.isActualTransactionActive();
            log.info("[{}]tx active >>> {}", callMethod, txActive);
            boolean readOnly = TransactionSynchronizationManager.isCurrentTransactionReadOnly();
            log.info("[{}]tx readOnly >>> {}", callMethod, readOnly);
        }
    }
}
