package com.jong1.datajpa.repository;

import com.jong1.datajpa.dto.MemberDto;
import com.jong1.datajpa.entity.Member;
import com.jong1.datajpa.entity.Team;
import jakarta.persistence.EntityManager;
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

    @Autowired
    private EntityManager em;

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
        int offset = 0;
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

    @Test
    void bulkUpdate() {
        // given
        memberRepository.save(Member.builder().username("member1").age(10).build());
        memberRepository.save(Member.builder().username("member2").age(18).build());
        memberRepository.save(Member.builder().username("member3").age(20).build());
        memberRepository.save(Member.builder().username("member4").age(21).build());
        memberRepository.save(Member.builder().username("member5").age(40).build());
        memberRepository.save(Member.builder().username("member6").age(19).build());
        memberRepository.save(Member.builder().username("member7").age(17).build());

        // when
        int result = memberRepository.bulkAgePlus(20);

        // bulk연산은 영속성 컨텍스트를 무시하고 DB에 직접 쿼리를 날린다. 그러다보니 영속성 컨텍스트에 보관중인 객체들이 DB와 다른 값을 가지게 된다.
        List<Member> findMember = memberRepository.findByUsername("member5");
        Member member5 = findMember.get(0);
        System.out.println("member5 = " + member5);

        // then
        assertEquals(3, result);
    }

    @Test
    void findMemberLazy() {
        // given
        Team teamA = Team.builder().name("teamA").build();
        Team teamB = Team.builder().name("teamB").build();
        teamRepository.save(teamA);
        teamRepository.save(teamB);

        Member member1 = Member.builder().username("member1").age(10).team(teamA).build();
        Member member2 = Member.builder().username("member2").age(10).team(teamB).build();
        memberRepository.save(member1);
        memberRepository.save(member2);

        em.flush();
        em.clear();

        // when
        List<Member> members = memberRepository.findAll();//EntityGraph
//        List<Member> members = memberRepository.findMemberFetchJoin();

        members.forEach(member -> {
            System.out.println("member = " + member.getUsername());
            System.out.println("member.teamClass = " + member.getTeam().getClass());
            System.out.println("member.team = " + member.getTeam().getName());
        });

        // then
    }

    @Test
    void queryHint() {
        // given
        Member member1 = memberRepository.save(Member.builder().username("member1").age(10).build());
        em.flush();
        em.clear();

        // when
        Member findMember = memberRepository.findReadOnlyByUsername("member1");
        //변경감지가 되지 않는다, 영속성 컨텍스트에 비교할 객체를 복사해두지 않기 떄문에, 불가함
        findMember.setUsername("member2");

        em.flush();
        // then
    }


    @Test
    void lock() {
        // given
        Member member1 = memberRepository.save(Member.builder().username("member1").age(10).build());
        em.flush();
        em.clear();

        // when DB의 Lock?
        List<Member> findMember = memberRepository.findLockByUsername("member1");
        // then
    }

    @Test
    public void callCustom() {
        List<Member> result = memberRepository.findMemberCustom();
    }

    @Test
    void projectonsTest() {
        // given
        Team teamA = Team.builder().name("teamA").build();
        Team teamB = Team.builder().name("teamB").build();
        teamRepository.save(teamA);
        teamRepository.save(teamB);

        Member member1 = Member.builder().username("member1").age(10).team(teamA).build();
        Member member2 = Member.builder().username("member2").age(10).team(teamB).build();
        memberRepository.save(member1);
        memberRepository.save(member2);

        em.flush();
        em.clear();

        // when
        List<UsernameOnly> result = memberRepository.findProjectionsByUsername("member1");
        result.forEach(System.out::println);

        result.forEach(u -> System.out.println("u = " + u.getUsername()));
    }

    @Test
    void projectonsTest2() {
        // given
        Team teamA = Team.builder().name("teamA").build();
        Team teamB = Team.builder().name("teamB").build();
        teamRepository.save(teamA);
        teamRepository.save(teamB);

        Member member1 = Member.builder().username("member1").age(10).team(teamA).build();
        Member member2 = Member.builder().username("member2").age(10).team(teamB).build();
        memberRepository.save(member1);
        memberRepository.save(member2);

        em.flush();
        em.clear();

        // when
        List<UsernameOnlyDto> result = memberRepository.findProjectionsDtoByUsername("member1");
        result.forEach(System.out::println);

        result.forEach(u -> System.out.println("u = " + u.getUsername()));
    }

    @Test
    void projectonsTest3() {
        // given
        Team teamA = Team.builder().name("teamA").build();
        Team teamB = Team.builder().name("teamB").build();
        teamRepository.save(teamA);
        teamRepository.save(teamB);

        Member member1 = Member.builder().username("member1").age(10).team(teamA).build();
        Member member2 = Member.builder().username("member2").age(10).team(teamB).build();
        memberRepository.save(member1);
        memberRepository.save(member2);

        em.flush();
        em.clear();

        // when
        List<UsernameOnlyDto> result = memberRepository.findProjectionsByUsername("member1", UsernameOnlyDto.class);
        result.forEach(System.out::println);

        result.forEach(u -> System.out.println("u = " + u.getUsername()));
    }

}