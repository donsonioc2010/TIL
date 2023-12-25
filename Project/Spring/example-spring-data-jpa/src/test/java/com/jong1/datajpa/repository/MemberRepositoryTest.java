package com.jong1.datajpa.repository;

import com.jong1.datajpa.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void testMember() {
        // given
        Member savedMember = memberRepository.save(
                Member.builder()
                        .username("memberA")
                        .build()
        );

        // when
        Member findMember = memberRepository.findById(savedMember.getId()).get();

        // then
        assertEquals(savedMember, findMember);
        assertEquals(savedMember.getId(), findMember.getId());
        assertEquals(savedMember.getUsername(), findMember.getUsername());
    }

    @Test
    void basicCRUD() {
        Member member1 = Member.builder().username("member1").build();
        Member member2 = Member.builder().username("member2").build();

        memberRepository.save(member1);
        memberRepository.save(member2);

        // 단건 검증
        Member findMember1 = memberRepository.findById(member1.getId()).get();
        Member findMember2 = memberRepository.findById(member2.getId()).get();
        assertEquals(member1, findMember1);
        assertEquals(member2, findMember2);

        // 리스트 검증
        assertEquals(memberRepository.findAll().size(), 2);

        // 카운트 검증
        assertEquals(memberRepository.count(), 2);

        // 삭제 검증
        memberRepository.delete(member1);
        memberRepository.delete(member2);

        long deletedCount = memberRepository.count();
        assertEquals(deletedCount, 0);
    }

}