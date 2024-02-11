package com.jong1.jdbc.repository;

import com.jong1.jdbc.domain.Member;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.context.jdbc.Sql;

import java.sql.SQLException;
import java.util.NoSuchElementException;

import static com.jong1.jdbc.connection.ConnectionConst.PASSWORD;
import static com.jong1.jdbc.connection.ConnectionConst.URL;
import static com.jong1.jdbc.connection.ConnectionConst.USERNAME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

// Embedded H2를 사용한 테스트를 작성할 경우, SpringBoot가 실행되지 않으면 H2가 사용이 불가능하기 떄문에 필요함.
@Slf4j
@SpringBootTest
@Sql(scripts = {"classpath:sql/schema.sql", "classpath:sql/data.sql"})
class MemberRepositoryV1Test {
    MemberRepositoryV1 memberRepositoryV1;

    @BeforeEach
    void beforeEach() {
        // 기본 DriverManager를 사용하여 매 새로운 커넥션 획득
//        DriverManagerDataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);

        // HikariCP를 사용하여 커넥션 풀링
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);

        memberRepositoryV1 = new MemberRepositoryV1(dataSource);
    }

    @Test
    void crud() throws SQLException {
        // save
        Member member = new Member("memberV1", 10000);
        memberRepositoryV1.save(member);

        // findById
        Member findMember = memberRepositoryV1.findById(member.getMemberId());
        log.info("findMember >>> {}", findMember);
        assertEquals(member, findMember);

        // update: money 10000 -> 20000
        memberRepositoryV1.update(member.getMemberId(), 20000);
        Member updateMember = memberRepositoryV1.findById(member.getMemberId());
        assertEquals(20000, updateMember.getMoney());

        //delete
        memberRepositoryV1.delete(member.getMemberId());
        assertThrowsExactly(
            NoSuchElementException.class,
            () -> memberRepositoryV1.findById(member.getMemberId()),
            "Member Not Found MemberId >>> " + member.getMemberId()
        );
    }

}