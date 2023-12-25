package com.jong1.datajpa.repository;

import com.jong1.datajpa.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
class MemberJpaRepositoryTest {

    @Autowired
    private MemberJpaRepository memberJpaRepository;


    @Test
    public void testMember() {
        // given
        Member savedMember = memberJpaRepository.save(
                Member.builder()
                        .username("memberA")
                        .build()
        );

        // when
        Member findMember = memberJpaRepository.find(savedMember.getId());

        // then
        assertEquals(savedMember, findMember);
        assertEquals(savedMember.getId(), findMember.getId());
        assertEquals(savedMember.getUsername(), findMember.getUsername());
    }


}