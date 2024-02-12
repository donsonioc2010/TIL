package com.jong1.jdbc.service;

import static com.jong1.jdbc.connection.ConnectionConst.PASSWORD;
import static com.jong1.jdbc.connection.ConnectionConst.URL;
import static com.jong1.jdbc.connection.ConnectionConst.USERNAME;
import com.jong1.jdbc.domain.Member;
import com.jong1.jdbc.repository.MemberRepositoryV3;
import java.sql.SQLException;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * 트랜잭션 - 커넥션 파라미터 전달 방식 동기화
 */
@Slf4j
@SpringBootTest
@Sql(scripts = {"classpath:sql/schema.sql", "classpath:sql/data.sql"})
class MemberServiceV3_3Test {

    public static final String MEMBER_A = "memberA";
    public static final String MEMBER_B = "memberB";
    public static final String MEMBER_EX = "ex";

    @Autowired
    private MemberRepositoryV3 memberRepository;
    @Autowired
    private MemberServiceV3_3 memberService;

    @TestConfiguration
    static class TestConfig {

        @Bean
        DataSource dataSource() {
            return new DriverManagerDataSource(URL, USERNAME, PASSWORD);
        }

        @Bean
        PlatformTransactionManager transactionManager() {
            return new DataSourceTransactionManager(dataSource());
        }

        @Bean
        MemberRepositoryV3 memberRepositoryV3() {
            return new MemberRepositoryV3(dataSource());
        }

        @Bean
        MemberServiceV3_3 memberServiceV3_3() {
            return new MemberServiceV3_3(memberRepositoryV3());
        }
    }

    @Test
    @DisplayName("AOP 검증")
    void aopCheck() {
        log.info("memberService >>> {}", this.memberService.getClass());
        log.info("memberRepository >>> {}", this.memberRepository.getClass());

        Assertions.assertTrue(AopUtils.isAopProxy(this.memberService));
        Assertions.assertFalse(AopUtils.isAopProxy(this.memberRepository));
    }

    @Test
    @DisplayName("정상 이체")
    void accountTransfer() throws SQLException {
        // given
        Member memberA = new Member(MEMBER_A, 10000);
        Member memberB = new Member(MEMBER_B, 10000);

        memberRepository.save(memberA);
        memberRepository.save(memberB);

        // when
        memberService.accountTransfer(MEMBER_A, MEMBER_B, 2000);

        // then
        Member findMemberA = memberRepository.findById(MEMBER_A);
        Member findMemberB = memberRepository.findById(MEMBER_B);

        Assertions.assertEquals(8000, findMemberA.getMoney());
        Assertions.assertEquals(12000, findMemberB.getMoney());
    }

    @Test
    @DisplayName("이체중 예외 발생")
    void accountTransferException() throws SQLException {
        // given
        Member memberA = new Member(MEMBER_A, 10000);
        Member memberEx = new Member(MEMBER_EX, 10000);

        memberRepository.save(memberA);
        memberRepository.save(memberEx);

        // when
        Assertions.assertThrowsExactly(
            IllegalStateException.class,
            () -> memberService.accountTransfer(MEMBER_A, MEMBER_EX, 2000),
            "이채중 예외 발생"
        );

        // then
        Member findMemberA = memberRepository.findById(MEMBER_A);
        Member findMemberEx = memberRepository.findById(MEMBER_EX);

        Assertions.assertEquals(10000, findMemberA.getMoney());
        Assertions.assertEquals(10000, findMemberEx.getMoney()); //트랜잭션 적용을 하지 않아 금액감소만 이뤄짐
    }
}