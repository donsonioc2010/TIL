package com.jong1.jdbc.service;

import com.jong1.jdbc.domain.Member;
import com.jong1.jdbc.repository.MemberRepository;
import com.jong1.jdbc.repository.MemberRepositoryV4_1;
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
import org.springframework.test.context.jdbc.Sql;

/**
 * 트랜잭션 - DataSource, TransactionManager 주입
 */
@Slf4j
@SpringBootTest
@Sql(scripts = {"classpath:sql/schema.sql", "classpath:sql/data.sql"})
class MemberServiceV4Test {

    public static final String MEMBER_A = "memberA";
    public static final String MEMBER_B = "memberB";
    public static final String MEMBER_EX = "ex";

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MemberServiceV4 memberService;

    @TestConfiguration
    static class TestConfig {

        private final DataSource dataSource;

        public TestConfig(DataSource dataSource) {
            this.dataSource = dataSource;
        }

        @Bean
        MemberRepository memberRepositoryV4() {
            return new MemberRepositoryV4_1(this.dataSource);
        }

        @Bean
        MemberServiceV4 memberServiceV3_3() {
            return new MemberServiceV4(memberRepositoryV4());
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
    void accountTransfer()  {
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
    void accountTransferException()  {
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