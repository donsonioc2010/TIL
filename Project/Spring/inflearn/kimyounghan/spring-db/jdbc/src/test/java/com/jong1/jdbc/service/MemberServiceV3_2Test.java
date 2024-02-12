package com.jong1.jdbc.service;

import static com.jong1.jdbc.connection.ConnectionConst.PASSWORD;
import static com.jong1.jdbc.connection.ConnectionConst.URL;
import static com.jong1.jdbc.connection.ConnectionConst.USERNAME;
import com.jong1.jdbc.domain.Member;
import com.jong1.jdbc.repository.MemberRepositoryV3;
import java.sql.SQLException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
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
class MemberServiceV3_2Test {

    public static final String MEMBER_A = "memberA";
    public static final String MEMBER_B = "memberB";
    public static final String MEMBER_EX = "ex";

    private MemberRepositoryV3 memberRepository;
    private MemberServiceV3_2 memberService;

    @BeforeEach
    void before() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
        memberRepository = new MemberRepositoryV3(dataSource);
        PlatformTransactionManager transactionManager =
            new DataSourceTransactionManager(dataSource);

        memberService = new MemberServiceV3_2(transactionManager, memberRepository);
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