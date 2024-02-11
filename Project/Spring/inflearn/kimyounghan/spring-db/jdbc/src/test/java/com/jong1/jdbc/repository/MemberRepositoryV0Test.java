package com.jong1.jdbc.repository;

import com.jong1.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

// Embedded H2를 사용한 테스트를 작성할 경우, SpringBoot가 실행되지 않으면 H2가 사용이 불가능하기 떄문에 필요함.
@Slf4j
@SpringBootTest
@Sql(scripts = {"classpath:sql/schema.sql", "classpath:sql/data.sql"})
class MemberRepositoryV0Test {
    MemberRepositoryV0 repository = new MemberRepositoryV0();

    @Test
    void crud() throws SQLException {
        // save
        Member member = new Member("memberV0", 10000);
        repository.save(member);

        // findById
        Member findMember = repository.findById(member.getMemberId());
        log.info("findMember >>> {}", findMember);
        assertEquals(member, findMember);

        // update: money 10000 -> 20000
        repository.update(member.getMemberId(), 20000);
        Member updateMember = repository.findById(member.getMemberId());
        assertEquals(20000, updateMember.getMoney());

        //delete
        repository.delete(member.getMemberId());
        assertThrowsExactly(
            NoSuchElementException.class,
            () -> repository.findById(member.getMemberId()),
            "Member Not Found MemberId >>> " + member.getMemberId()
        );
    }

}