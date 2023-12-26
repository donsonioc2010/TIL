package com.jong1.datajpa.repository;

import com.jong1.datajpa.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
@Rollback(value = false) // 테스트가 끝나고 롤백할지 여부, 테스트가 끝나면 롤백이 되는데, 그러면 쿼리를 직접 확인이 불가해서 하는 설정, 꼭 하진 않아도 된다.
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

    @Test
    void basicCRUD() {
        Member member1 = Member.builder().username("member1").build();
        Member member2 = Member.builder().username("member2").build();

        memberJpaRepository.save(member1);
        memberJpaRepository.save(member2);

        // 단건 검증
        Member findMember1 = memberJpaRepository.findById(member1.getId()).get();
        Member findMember2 = memberJpaRepository.findById(member2.getId()).get();
        assertEquals(member1, findMember1);
        assertEquals(member2, findMember2);

        // 리스트 검증
        assertEquals(memberJpaRepository.findAll().size(), 2);

        // 카운트 검증
        assertEquals(memberJpaRepository.count(), 2);

        // 삭제 검증
        memberJpaRepository.delete(member1);
        memberJpaRepository.delete(member2);

        long deletedCount = memberJpaRepository.count();
        assertEquals(deletedCount, 0);
    }

    @Test
    void findByUsernameAndAgeGreaterThen() {
        // given
        Member member1 = Member.builder().username("AAA").age(10).build();
        Member member2 = Member.builder().username("AAA").age(20).build();
        memberJpaRepository.save(member1);
        memberJpaRepository.save(member2);

        // when
        List<Member> result = memberJpaRepository.findByUsernameAndAgeGreaterThan("AAA", 15);

        // then
        assertEquals("AAA", result.get(0).getUsername());
        assertEquals(20, result.get(0).getAge());
        assertEquals(1, result.size());
    }
}