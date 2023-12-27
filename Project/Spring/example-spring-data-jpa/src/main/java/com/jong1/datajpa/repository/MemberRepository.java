package com.jong1.datajpa.repository;


import com.jong1.datajpa.dto.MemberDto;
import com.jong1.datajpa.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

    /**
     * limit는 Spring Data JPA Docs확인
     *
     * @return
     */
    List<Member> findTop3By();

    /**
     * @param username
     * @return
     * @Param을 작성하는 이유는 NamedQuery를 사용할 때, 파라미터를 바인딩할 때 사용한다.
     */
    @Query(name = "Member.findByUsername")
    List<Member> findByUsername(@Param("username") String username);

    /**
     * Repository에서 직접 쿼리를 저의하는 방법
     *
     * @param username
     * @param age
     * @return
     */
    @Query("select m from Member m where m.username = :username and m.age = :age")
    List<Member> findUser(@Param("username") String username, @Param("age") int age);

    @Query("select m.username from Member m")
    List<String> findUsernameList();

    @Query("select new com.jong1.datajpa.dto.MemberDto(m.id, m.username, t.name) from Member m join m.team t")
    List<MemberDto> findMemberDto();

    /**
     * 두개다 가능하다, 컬렉션 파라미터 바인딩
     *
     * @param names
     * @return
     */
    @Query("select m from Member m where m.username in :names")
//    List<Member> findByNames(@Param("names") List<String> names);
    List<Member> findByNames(@Param("names") Collection<String> names);

//    Page<Member> findByAge(int age, Pageable pageable);

    /**
     * CountQuery를 분리하는 방법
     *
     * @param age
     * @param pageable
     * @return
     */
    @Query(value = "select m from Member m left join m.team t",
            countQuery = "select count(m) from Member m")
    Page<Member> findByAge(int age, Pageable pageable);

    // @Modifying이 존재해야 update, delete가 가능하다.
    // clearAutomaticcally는 영속성 컨텍스트를 자동으로 초기화해준다.
//    @Modifying
    @Modifying(clearAutomatically = true)
    @Query("update Member m set m.age=m.age+1 where m.age >= :age")
    int bulkAgePlus(@Param("age") int age);
}
