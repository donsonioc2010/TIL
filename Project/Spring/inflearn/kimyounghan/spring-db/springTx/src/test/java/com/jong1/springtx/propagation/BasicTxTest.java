package com.jong1.springtx.propagation;

import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;

@Slf4j
@SpringBootTest
public class BasicTxTest {

    @Autowired
    private PlatformTransactionManager txManager;

    @TestConfiguration
    static class Config {

        @Bean
        public PlatformTransactionManager transactionManager(DataSource dataSource) {
            return new DataSourceTransactionManager(dataSource); //JDBC
        }
    }

    @Test
    void commit() {
        log.info("트랜잭션 시작");
        TransactionStatus status = txManager.getTransaction(new DefaultTransactionAttribute());

        log.info("트랜잭션 커밋 시작");
        txManager.commit(status);
        log.info("트랜잭션 커밋 완료");
    }

    @Test
    void rollback() {
        log.info("트랜잭션 시작");
        TransactionStatus status = txManager.getTransaction(new DefaultTransactionAttribute());

        log.info("트랜잭션 롤백 시작");
        txManager.rollback(status);
        log.info("트랜잭션 롤백 완료");
    }

    /**
     * 실제 HikariCP에서는 같은 Connection을 연결해 주지만 Hikari가 해당 Connection을 기반으로 만든 새로운 Transaction이기 떄문에 서로
     * 다른 트랜잭션이다
     */
    @Test
    void double_commit() {
        log.info("트랜잭션1 시작");
        TransactionStatus tx1 = txManager.getTransaction(new DefaultTransactionAttribute());

        log.info("트랜잭션1 커밋 시작");
        txManager.commit(tx1);
        log.info("트랜잭션1 커밋 완료");

        log.info("트랜잭션2 시작");
        TransactionStatus tx2 = txManager.getTransaction(new DefaultTransactionAttribute());

        log.info("트랜잭션2 커밋 시작");
        txManager.commit(tx2);
        log.info("트랜잭션2 커밋 완료");
    }

    // 1번은 Commit, 2번은 Rollback, 서로 두개는 다른 트랜잭션이다.
    @Test
    void double_commit_rollback() {
        log.info("트랜잭션1 시작");
        TransactionStatus tx1 = txManager.getTransaction(new DefaultTransactionAttribute());

        log.info("트랜잭션1 커밋 시작");
        txManager.commit(tx1);
        log.info("트랜잭션1 커밋 완료");

        log.info("트랜잭션2 시작");
        TransactionStatus tx2 = txManager.getTransaction(new DefaultTransactionAttribute());

        log.info("트랜잭션2 롤백 시작");
        txManager.rollback(tx2);
        log.info("트랜잭션2 롤백 완료");
    }

    @Test
    void innerCommit() {
        log.info("외부 트랜잭션 시작");
        TransactionStatus outer = txManager.getTransaction(new DefaultTransactionAttribute());
        log.info("Outer.isNewTransaction() >>> {}", outer.isNewTransaction()); // 새로운 트랜잭션인지 여부 확인

        log.info("내부 트랜잭션 시작");
        TransactionStatus inner = txManager.getTransaction(new DefaultTransactionAttribute());
        log.info("Inner.isNewTransaction() >>> {}", inner.isNewTransaction()); // 새로운 트랜잭션인지 여부 확인

        log.info("내부 트랜잭션 커밋 시작");
        txManager.commit(inner);
        log.info("내부 트랜잭션 커밋 완료");

        log.info("외부 트랜잭션 커밋 시작");
        txManager.commit(outer);
        log.info("외부 트랜잭션 커밋 완료");
    }

    @Test
    void outerRollback() {
        log.info("외부 트랜잭션 시작");
        TransactionStatus outer = txManager.getTransaction(new DefaultTransactionAttribute());

        log.info("내부 트랜잭션 시작");
        TransactionStatus inner = txManager.getTransaction(new DefaultTransactionAttribute());

        log.info("내부 트랜잭션 커밋 시작");
        txManager.commit(inner);
        log.info("내부 트랜잭션 커밋 완료");

        log.info("외부 트랜잭션 롤백 시작");
        txManager.rollback(outer);
        log.info("외부 트랜잭션 롤백 완료");
    }


    @Test
    void innerRollback() {
        log.info("외부 트랜잭션 시작");
        TransactionStatus outer = txManager.getTransaction(new DefaultTransactionAttribute());

        log.info("내부 트랜잭션 시작");
        TransactionStatus inner = txManager.getTransaction(new DefaultTransactionAttribute());

        log.info("내부 트랜잭션 롤백 시작");
        txManager.rollback(inner);
        log.info("내부 트랜잭션 롤백 완료");

        log.info("외부 트랜잭션 커밋 시작");
        Assertions.assertThrows(UnexpectedRollbackException.class, () -> txManager.commit(outer));
        log.info("외부 트랜잭션 커밋 완료");
    }

    @Test
    void innerRollbackRequiresNew() {
        log.info("외부 트랜잭션 시작");
        TransactionStatus outer = txManager.getTransaction(new DefaultTransactionAttribute());
        log.info("Outer.isNewTransaction() >>> {}", outer.isNewTransaction()); // 새로운 트랜잭션인지 여부 확인

        log.info("내부 트랜잭션 시작");
        DefaultTransactionAttribute definition = new DefaultTransactionAttribute();
        definition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        TransactionStatus inner = txManager.getTransaction(definition);
        log.info("Inner.isNewTransaction() >>> {}", inner.isNewTransaction()); // 새로운 트랜잭션인지 여부 확인

        log.info("내부 트랜잭션 롤백 시작");
        txManager.rollback(inner);
        log.info("내부 트랜잭션 롤백 완료");

        log.info("외부 트랜잭션 커밋 시작");
        txManager.commit(outer);
        log.info("외부 트랜잭션 커밋 완료");
    }
}
