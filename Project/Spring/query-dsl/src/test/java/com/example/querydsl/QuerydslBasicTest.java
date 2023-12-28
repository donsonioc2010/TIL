package com.example.querydsl;

import com.example.querydsl.entity.Member;
import com.example.querydsl.entity.QMember;
import com.example.querydsl.entity.QTeam;
import com.example.querydsl.entity.Team;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class QuerydslBasicTest {
    @Autowired
    private EntityManager em;
    private JPAQueryFactory queryFactory;

    @BeforeEach
    void before() {
        queryFactory = new JPAQueryFactory(em);
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 20, teamA);

        Member member3 = new Member("member3", 30, teamB);
        Member member4 = new Member("member4", 40, teamB);

        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);
    }

    @Test
    void startJPQL() {
        // member1을 찾아라.
        String qlString = "select m from Member m where m.username = :username";
        Member findMember = em.createQuery(qlString, Member.class)
                .setParameter("username", "member1")
                .getSingleResult();
        System.out.println("findMember = " + findMember);
        assertEquals(findMember.getUsername(), "member1");
    }

    @Test
    void startQuerydsl() {
        QMember m = new QMember("m"); //같은 테이블을 조인해야 할 경우에만 다른 Variable로 생성해 사용한다.
        Member findMember = queryFactory
                .select(m)
                .from(m)
                .where(m.username.eq("member1")) // 파라미터 바인딩 처리
                .fetchOne();

        assertEquals(findMember.getUsername(), "member1");
    }

    @Test
    void startQuerydsl2() {
        Member findMember = queryFactory
                .select(QMember.member)
                .from(QMember.member)
                .where(QMember.member.username.eq("member1")) // 파라미터 바인딩 처리
                .fetchOne();

        assertEquals(findMember.getUsername(), "member1");
    }

    @Test
    void search() {
        Member findMember = queryFactory.select(QMember.member)
                .from(QMember.member)
                .where(
                        QMember.member.username.eq("member1")
                                .and(QMember.member.age.eq(10)))
                .fetchOne();

        assertEquals(findMember.getUsername(), "member1");
    }

    @Test
    void searchAndParam() {
        // ,으로 만 하는건 And조건의 연속이다, 또한 null은 무시된다.
        Member findMember = queryFactory.select(QMember.member)
                .from(QMember.member)
                .where(
                        QMember.member.username.eq("member1"),
                        QMember.member.age.eq(10))
                .fetchOne();

        assertEquals(findMember.getUsername(), "member1");
    }

    @Test
    void resultFetch() {
        List<Member> fetch = queryFactory.selectFrom(QMember.member)
                .fetch();

        Member member = queryFactory.selectFrom(QMember.member)
                .fetchOne();

        Member member1 = queryFactory.selectFrom(QMember.member)
                .fetchFirst();
    }

    @Test
    void fetchResults_Deprecated() {
        // Deprecated
        // QueryResults<Member> results = queryFactory.selectFrom(QMember.member)
        //         .fetchResults();
        // long total = results.getTotal();
        // List<Member> content = results.getResults();

        // 변경
        Long totalCount = queryFactory
                //.select(Wildcard.count) //select count(*)
                .select(QMember.member.count()) //select count(member.id)
                .from(QMember.member)
                .fetchOne();
        System.out.println("totalCount = " + totalCount);
    }

    /**
     * 회원 정렬 순서
     * 1. 회원 나이 내림차순(desc)
     * 2. 회원 이름 올림차순(asc)
     * 단, 2에서 회원 이름이 없으면 마지막에 출력(nulls last)
     */
    @Test
    void sort() {
        em.persist(new Member(null, 100));
        em.persist(new Member("member5", 100));
        em.persist(new Member("member6", 100));

        queryFactory.select(QMember.member)
                .from(QMember.member)
                .orderBy(
                        QMember.member.age.desc(),
                        QMember.member.username.asc().nullsLast())
                .fetch()
                .forEach(m -> {
                    System.out.println("m = " + m);
                });
    }

    @Test
    void paging1() {
        List<Member> result = queryFactory.selectFrom(QMember.member)
                .orderBy(QMember.member.username.desc())
                .offset(1)
                .limit(2)
                .fetch();

        assertEquals(result.size(), 2);
        result.forEach(System.out::println);
    }

    @Test
    void paging2() {
        List<Member> result = queryFactory.selectFrom(QMember.member)
                .orderBy(QMember.member.username.desc())
                .offset(1)
                .limit(2)
                .fetch();

        Long totalCount = queryFactory
                //.select(Wildcard.count) //select count(*)
                .select(QMember.member.count()) //select count(member.id)
                .from(QMember.member)
                .fetchOne();


        assertEquals(result.size(), 2);
        assertEquals(totalCount, 4);
    }

    // 집합
    @Test
    void aggregation() {
        Tuple result = queryFactory.select(
                        QMember.member.count(),
                        QMember.member.age.sum(),
                        QMember.member.age.avg(),
                        QMember.member.age.max(),
                        QMember.member.age.min()
                ).from(QMember.member)
                .fetchOne();

        assertEquals(result.get(QMember.member.count()), 4);
        assertEquals(result.get(QMember.member.age.sum()), 100);
        assertEquals(result.get(QMember.member.age.avg()), 25);
        assertEquals(result.get(QMember.member.age.max()), 40);
    }

    /**
     * 팀의 이름과 각 팀의 평균 연령을 구해라.
     */
    @Test
    void group() {
        List<Tuple> result = queryFactory
                .select(QTeam.team.name, QMember.member.age.avg())
                .from(QMember.member)
                .join(QMember.member.team, QTeam.team)
                .groupBy(QTeam.team.name)
//                .having(QMember.member.age.avg().gt(10)) //가능
                .fetch();

        Tuple teamA = result.get(0);
        Tuple teamB = result.get(1);

        assertEquals(teamA.get(QTeam.team.name), "teamA");
        assertEquals(teamA.get(QMember.member.age.avg()), 15);

        assertEquals(teamB.get(QTeam.team.name), "teamB");
        assertEquals(teamB.get(QMember.member.age.avg()), 35);
    }
}
