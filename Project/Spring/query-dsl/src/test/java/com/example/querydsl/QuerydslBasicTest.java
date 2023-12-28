package com.example.querydsl;

import com.example.querydsl.dto.MemberDto;
import com.example.querydsl.dto.MemberDto2;
import com.example.querydsl.dto.QMemberDto2;
import com.example.querydsl.dto.UserDto;
import com.example.querydsl.entity.Member;
import com.example.querydsl.entity.QMember;
import com.example.querydsl.entity.QTeam;
import com.example.querydsl.entity.Team;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
                .where(QMember.member.username.eq("member1"))
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

    /**
     * 팀 A에 소속된 모든 회원
     */
    @Test
    void join() {
        List<Member> result = queryFactory.selectFrom(QMember.member)
                .join(QMember.member.team, QTeam.team)
//                .innerJoin(QMember.member.team, QTeam.team)//inner join
//                .leftJoin(QMember.member.team, QTeam.team) //left outer join
//                .rightJoin(QMember.member.team, QTeam.team) //right outer join
                .where(QTeam.team.name.eq("teamA"))
                .fetch();

        Assertions.assertThat(result)
                .extracting("username")
                .containsExactly("member1", "member2");
    }

    /**
     * 연관이 없는 엔티티 외부 조인
     */
    @Test
    void theta_join() {
        em.persist(new Member("teamA"));
        em.persist(new Member("teamB"));

        List<Member> result = queryFactory
                .select(QMember.member)
                .from(QMember.member, QTeam.team)
                .where(QMember.member.username.eq(QTeam.team.name))
                .fetch();

        result.forEach(System.out::println);

        Assertions.assertThat(result)
                .extracting("username")
                .containsExactly("teamA", "teamB");
    }

    /**
     * 예) 회원과 팀을 조인하면서, 팀 이름이 teamA인 팀만 조인, 회원은 모두 조회
     * JPQL: select m, t from Member m left join m.team t on t.name = 'teamA'
     */
    @Test
    void join_on_filtering() {
        List<Tuple> result = queryFactory
                .select(QMember.member, QTeam.team)
                .from(QMember.member)
                .leftJoin(QMember.member.team, QTeam.team)
//                .join(QMember.member.team, QTeam.team) // InnerJoin은 사실상 Where절과 같다.
                .on(QTeam.team.name.eq("teamA"))
                .fetch();

        result.forEach(System.out::println);
    }

    /**
     * 연관관계가 없는 엔티티 외부 조인
     * 회원의 이름이 팀 이름과 같은 대상 외부 조인
     */
    @Test
    void join_on_no_relation() {
        em.persist(new Member("teamA"));
        em.persist(new Member("teamB"));
        em.persist(new Member("teamC"));

        List<Tuple> result = queryFactory
                .select(QMember.member, QTeam.team)
                .from(QMember.member)
                .leftJoin(QTeam.team)
                .on(QMember.member.username.eq(QTeam.team.name))
                .fetch();

        result.forEach(System.out::println);
    }


    @PersistenceUnit
    EntityManagerFactory emf;

    @Test
    void fetchJoinNo() {
        em.flush();
        em.clear();

        Member findMember = queryFactory
                .selectFrom(QMember.member)
                .where(QMember.member.username.eq("member1"))
                .fetchOne();

        // fetch join을 하지 않으면 Lazy Loading으로 인해 Team을 가져오지 않는다.
        // getPersistenceUnitUtil.isLoaded()를 통해 Lazy Loading 여부를 확인할 수 있다.
        boolean loaded = emf.getPersistenceUnitUtil().isLoaded(findMember.getTeam());
        assertFalse(loaded);
    }

    @Test
    void fetchJoinUse() {
        em.flush();
        em.clear();

        Member findMember = queryFactory
                .selectFrom(QMember.member)
                .join(QMember.member.team, QTeam.team).fetchJoin()
                .where(QMember.member.username.eq("member1"))
                .fetchOne();

        boolean loaded = emf.getPersistenceUnitUtil().isLoaded(findMember.getTeam());
        assertTrue(loaded);
    }

    /**
     * 나이가 가장 많은 회원 조회
     */
    @Test
    void subQuery() {
        // given
        QMember memberSub = new QMember("memberSub");

        List<Member> result = queryFactory
                .selectFrom(QMember.member)
                .where(QMember.member.age.eq(
                        JPAExpressions
                                .select(memberSub.age.max())
                                .from(memberSub)
                ))
                .fetch();

        assertEquals(result.get(0).getAge(), 40);
    }

    /**
     * 나이가 평균 이상인 회원
     */
    @Test
    void subQueryGoe() {
        // given
        QMember memberSub = new QMember("memberSub");

        List<Member> result = queryFactory
                .selectFrom(QMember.member)
                .where(QMember.member.age.goe(
                        JPAExpressions
                                .select(memberSub.age.avg())
                                .from(memberSub)
                ))
                .fetch();

        Assertions.assertThat(result)
                .extracting("age")
                .containsExactly(30, 40);
    }

    @Test
    void subQueryIn() {
        // given
        QMember memberSub = new QMember("memberSub");

        List<Member> result = queryFactory
                .selectFrom(QMember.member)
                .where(QMember.member.age.in(
                        JPAExpressions
                                .select(memberSub.age)
                                .from(memberSub)
                                .where(memberSub.age.gt(10))
                ))
                .fetch();

        Assertions.assertThat(result)
                .extracting("age")
                .containsExactly(20, 30, 40);
    }

    @Test
    void selectSubQuery() {
        // given
        QMember memberSub = new QMember("memberSub");

        List<Tuple> result = queryFactory
                .select(QMember.member.username,
                        JPAExpressions
                                .select(memberSub.age.avg())
                                .from(memberSub)
                )
                .from(QMember.member)
                .fetch();

        result.forEach(System.out::println);
    }

    /**
     * Case문
     */
    @Test
    void basicCase() {
        List<String> result = queryFactory
                .select(
                        QMember.member.age
                                .when(10).then("열살")
                                .when(20).then("스무살")
                                .otherwise("기타")
                )
                .from(QMember.member)
                .fetch();

        result.forEach(System.out::println);
    }

    @Test
    void complexCase() {
        List<String> result = queryFactory
                .select(
                        new CaseBuilder()
                                .when(QMember.member.age.between(0, 20)).then("0~20살")
                                .when(QMember.member.age.between(21, 30)).then("21~30살")
                                .otherwise("기타")
                )
                .from(QMember.member)
                .fetch();

        result.forEach(System.out::println);
    }

    @Test
    void constant() {
        // given
        List<Tuple> result = queryFactory
                .select(QMember.member.username, Expressions.constant("A"))
                .from(QMember.member)
                .fetch();
        /* 결과
        [member1, A]
        [member2, A]
        [member3, A]
        [member4, A]
         */
        result.forEach(System.out::println);
    }

    @Test
    void concat() {
        // given
        List<String> result = queryFactory
                .select(QMember.member.username.concat("_").concat(QMember.member.age.stringValue()))
                .from(QMember.member)
                .fetch();
        /* 결과
        member1_10
        member2_20
        member3_30
        member4_40
         */
        result.forEach(System.out::println);
    }

    @Test
    void simpleProjection() {
        List<String> result = queryFactory
                .select(QMember.member.username)
                .from(QMember.member)
                .fetch();

        result.forEach(System.out::println);
    }

    @Test
    void tupleProjection() {
        List<Tuple> result = queryFactory
                .select(QMember.member.username, QMember.member.age)
                .from(QMember.member)
                .fetch();

        result.forEach(tuple -> {
            String username = tuple.get(QMember.member.username);
            Integer age = tuple.get(QMember.member.age);
            System.out.println("username = " + username);
            System.out.println("age = " + age);
        });
    }

    /**
     * JPQL에서 DTO로 바로 조회
     */
    @Test
    void findDtoByJPQL() {
        List<MemberDto> resultList = em.createQuery("select new com.example.querydsl.dto.MemberDto(m.username, m.age) from Member m", MemberDto.class)
                .getResultList();

        resultList.forEach(System.out::println);
    }

    /**
     * bean은 setter를 통해 값을 주입한다는 의미이다.
     */
    @Test
    void findDtoWithQuerydslBySetter() {
        //bean의 의미는 setter를 통해 값을 주입한다는 의미이다.
        //기본 생성자는 필수이다.
        List<MemberDto> result = queryFactory
                .select(
                        Projections.bean(MemberDto.class,
                                QMember.member.username,
                                QMember.member.age
                        )
                ).from(QMember.member)
                .fetch();
        result.forEach(System.out::println);
    }

    /**
     * field의 경우에는 Getter와 Setter가 없어도 된다.
     */
    @Test
    void findDtoWithQuerydslByFields() {
        //기본 생성자는 필수이다.
        List<MemberDto> result = queryFactory
                .select(
                        Projections.fields(MemberDto.class,
                                QMember.member.username,
                                QMember.member.age
                        )
                ).from(QMember.member)
                .fetch();
        result.forEach(System.out::println);
    }

    /**
     * 생성자의 경우에는 타입이 일치하는 생성자가 필요하다.
     */
    @Test
    void findDtoWithQuerydslByConstructor() {
        //기본 생성자는 필수이다.
        List<MemberDto> result = queryFactory
                .select(
                        Projections.constructor(MemberDto.class,
                                QMember.member.username,
                                QMember.member.age
                        )
                ).from(QMember.member)
                .fetch();
        result.forEach(System.out::println);
    }

    /**
     * 파라미터의 명칭이 다른 경우, bean과 field방식은 null로 주입이 된다.
     * 변경은 as를 통해 가능하다.
     */
    @Test
    void findUserDto() {

        //기본 생성자는 필수이다.
        List<UserDto> result = queryFactory
                .select(
                        Projections.bean(UserDto.class,
                                QMember.member.username.as("name"),
                                QMember.member.age
                        )
                ).from(QMember.member)
                .fetch();

        System.out.println("=======result1======");
        result.forEach(System.out::println);

        //기본 생성자는 필수이다.
        List<UserDto> result2 = queryFactory
                .select(
                        Projections.fields(UserDto.class,
                                QMember.member.username.as("name"),
                                QMember.member.age
                        )
                ).from(QMember.member)
                .fetch();

        System.out.println("=======result42=====");
        result2.forEach(System.out::println);

        List<UserDto> result3 = queryFactory
                .select(
                        Projections.constructor(UserDto.class,
                                QMember.member.username.as("name"),
                                QMember.member.age
                        )
                ).from(QMember.member)
                .fetch();

        System.out.println("=======result3======");
        result3.forEach(System.out::println);


        QMember memberSub = new QMember("memberSub");
        List<UserDto> result4 = queryFactory
                .select(
                        Projections.fields(
                                UserDto.class,
                                QMember.member.username.as("name"),
                                ExpressionUtils.as(
                                        JPAExpressions
                                                .select(memberSub.member.age.max()).from(memberSub),
                                        "age"
                                )
                        )
                ).from(QMember.member)
                .fetch();
        System.out.println("=======result4======");
        result4.forEach(System.out::println);

        List<UserDto> result5 = queryFactory
                .select(
                        Projections.constructor(
                                UserDto.class,
                                QMember.member.username.as("name"),
                                QMember.member.age
                        )
                ).from(QMember.member)
                .fetch();
        System.out.println("=======result5======");
        result5.forEach(System.out::println);
    }

    @Test
    void findDtoByQueryProjection() {
        List<MemberDto2> result = queryFactory
                .select(
                        new QMemberDto2(
                                QMember.member.username,
                                QMember.member.age
                        )
                )
                .from(QMember.member)
                .fetch();

        result.forEach(System.out::println);
    }


    @Test
    void dynamicQuery_BoleanBuilder() {
        String usernameParam = "member1";
        Integer ageParam = 10;

        List<Member> result = searchMember1(usernameParam, ageParam);
        assertEquals(result.size(), 1);
    }

    private List<Member> searchMember1(String usernameCond, Integer ageCond) {
        BooleanBuilder builder = new BooleanBuilder();
        if(usernameCond != null) {
            builder.and(QMember.member.username.eq(usernameCond));
        }
        if(ageCond != null) {
            builder.and(QMember.member.age.eq(ageCond));
        }

        return queryFactory
                .selectFrom(QMember.member)
                .where(builder)
                .fetch();
    }
}
