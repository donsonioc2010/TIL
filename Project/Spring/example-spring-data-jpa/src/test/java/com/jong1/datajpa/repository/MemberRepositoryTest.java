package com.jong1.datajpa.repository;

import com.jong1.datajpa.entity.Member;
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


    @Test
    void findByUsernameAndAgeGreaterThen() {
        // given
        Member member1 = Member.builder().username("AAA").age(10).build();
        Member member2 = Member.builder().username("AAA").age(20).build();
        memberRepository.save(member1);
        memberRepository.save(member2);

        // when
        List<Member> result = memberRepository.findByUsernameAndAgeGreaterThan("AAA", 15);

        // then
        assertEquals("AAA", result.get(0).getUsername());
        assertEquals(20, result.get(0).getAge());
        assertEquals(1, result.size());
    }

    @Test
    void findTop3By() {
        Member member1 = Member.builder().username("AAA").age(10).build();
        Member member2 = Member.builder().username("AAA").age(20).build();
        Member member3 = Member.builder().username("AAA").age(30).build();
        Member member4 = Member.builder().username("AAA").age(40).build();
        Member member5 = Member.builder().username("AAA").age(50).build();
        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);
        memberRepository.save(member4);
        memberRepository.save(member5);

        memberRepository.findTop3By().forEach(System.out::println);

        assertEquals(memberRepository.findTop3By().size(), 3);
    }
}