package com.example.querydsl.repository;

import com.example.querydsl.entity.Member;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberRepositoryTest {
    @Autowired
    private EntityManager em;
    @Autowired
    private MemberJpaRepository memberJpaRepository;

    @Test
    void basicTest() {
        Member member = new Member("member1", 10);
        memberJpaRepository.save(member);

        Member findMember = memberJpaRepository.findById(member.getId()).get();
        assertEquals(member, findMember);

        List<Member> result1 = memberJpaRepository.findAll();
        assertEquals(1, result1.size());
        Assertions.assertThat(result1).containsExactly(member);

        List<Member> result1Querydsl = memberJpaRepository.findAllQuerydsl();
        assertEquals(1, result1Querydsl.size());
        Assertions.assertThat(result1Querydsl).containsExactly(member);

        List<Member> result2 = memberJpaRepository.findByUsername("member1");
        Assertions.assertThat(result2).containsExactly(member);

        List<Member> result2Querydsl = memberJpaRepository.findByUsernameQuerydsl("member1");
        Assertions.assertThat(result2Querydsl).containsExactly(member);
    }


}