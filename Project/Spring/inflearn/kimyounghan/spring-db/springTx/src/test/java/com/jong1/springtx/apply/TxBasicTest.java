package com.jong1.springtx.apply;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Slf4j
@SpringBootTest
public class TxBasicTest {

    @Autowired private BasicService basicService;

    @Test
    void proxyCheck() {
        log.info("AOP Class >>> {}", basicService.getClass());
        Assertions.assertTrue(AopUtils.isAopProxy(basicService)); //AOP가 적용된 클래스인지 확인가능
    }

    @Test
    void txTest() {
        basicService.tx();
        basicService.nonTx();
    }

    @TestConfiguration
    static class TxApplyBasicConfig {

        @Bean
        BasicService basicService() {
            return new BasicService();
        }
    }
    
    @Slf4j
    static class BasicService {
        @Transactional
        public void tx() {
            log.info("Call Tx");

            // Transaction의 존재여부를 확인하는 Manager Method
            boolean txActive = TransactionSynchronizationManager.isActualTransactionActive();

            log.info("tx active >>> {}" , txActive);
        }
        
        public void nonTx() {
            log.info("Call nonTx");

            // Transaction의 존재여부를 확인하는 Manager Method
            boolean txActive = TransactionSynchronizationManager.isActualTransactionActive();

            log.info("tx active >>> {}" , txActive);
        }
    }

}
