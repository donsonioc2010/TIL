package com.jong1.datajpa.repository;


import com.jong1.datajpa.dto.MemberDto;
import com.jong1.datajpa.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

    /**
     * limit는 Spring Data JPA Docs확인
     * @return
     */
    List<Member> findTop3By();

    /**
     * @Param을 작성하는 이유는 NamedQuery를 사용할 때, 파라미터를 바인딩할 때 사용한다.
     * @param username
     * @return
     */
    @Query(name = "Member.findByUsername")
    List<Member> findByUsername(@Param("username") String username);

    /**
     * Repository에서 직접 쿼리를 저의하는 방법
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

}
