package com.jong1.datajpa.repository;

import com.jong1.datajpa.dto.MemberDto;
import com.jong1.datajpa.entity.Member;
import com.jong1.datajpa.entity.Team;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private TeamRepository teamRepository;

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

    @Test
    void namedQuery() {
        // given
        Member member1 = Member.builder().username("AAA").age(10).build();
        Member member2 = Member.builder().username("BBB").age(20).build();
        memberRepository.save(member1);
        memberRepository.save(member2);

        // when
        List<Member> result = memberRepository.findByUsername("AAA");

        // then
        assertEquals(result.get(0), member1);
    }

    @Test
    void testQuery() {
        // given
        Member member1 = Member.builder().username("AAA").age(10).build();
        Member member2 = Member.builder().username("BBB").age(20).build();
        memberRepository.save(member1);
        memberRepository.save(member2);

        // when && then
        assertEquals(member1, memberRepository.findUser("AAA", 10).get(0));
        assertEquals(member2, memberRepository.findUser("BBB", 20).get(0));
    }

    @Test
    void findUsernameList() {
        // given
        Member member1 = Member.builder().username("AAA").age(10).build();
        Member member2 = Member.builder().username("BBB").age(20).build();
        memberRepository.save(member1);
        memberRepository.save(member2);

        // when && then
        List<String> usernameList = memberRepository.findUsernameList();
        for (String username : usernameList) {
            System.out.println("username = " + username);
        }
    }

    @Test
    void findMemberDto() {
        // given
        Team team = Team.builder().name("teamA").build();
        teamRepository.save(team);

        Member member1 = Member.builder().username("AAA").age(10).team(team).build();
        Member member2 = Member.builder().username("BBB").age(20).team(team).build();
        memberRepository.save(member1);
        memberRepository.save(member2);

        // when && then
        memberRepository.findMemberDto().forEach(System.out::println);
    }

    @Test
    void findByNames() {
        // given
        Team team = Team.builder().name("teamA").build();
        teamRepository.save(team);

        Member member1 = Member.builder().username("AAA").age(10).team(team).build();
        Member member2 = Member.builder().username("BBB").age(20).team(team).build();
        memberRepository.save(member1);
        memberRepository.save(member2);

        // when && then
        memberRepository.findByNames(List.of("AAA", "BBB")).forEach(System.out::println);
    }


    @Test
    void paging() {
        // given
        memberRepository.save(Member.builder().username("member1").age(10).build());
        memberRepository.save(Member.builder().username("member2").age(10).build());
        memberRepository.save(Member.builder().username("member3").age(10).build());
        memberRepository.save(Member.builder().username("member4").age(10).build());
        memberRepository.save(Member.builder().username("member5").age(10).build());
        memberRepository.save(Member.builder().username("member6").age(10).build());
        memberRepository.save(Member.builder().username("member7").age(10).build());

        int age = 10;
        int offset =0;
        int limit = 3;

        // Sorting은 Optional이다
        PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "username"));

        // when
        Page<Member> members = memberRepository.findByAge(age, pageRequest);

        // then
        assertEquals(3, members.getContent().size()); // 현재 페이지에 나온 Row갯수
        assertEquals(7, members.getTotalElements()); // 전체 Row갯수
        assertEquals(3, members.getTotalPages()); // 전체 페이지 갯수
        assertEquals(0, members.getNumber());   // 현재 페이지 번호
        assertTrue(members.isFirst());  //첫 페이지 여부 확인
        assertTrue(members.hasNext()); // 다음 페이지 여부 확인

        // Page내부의 결과를 변경하는방법
        members.map(member -> new MemberDto(member.getId(), member.getUsername(), null)).forEach(System.out::println);
    }
}