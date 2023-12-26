package com.jong1.datajpa.repository;


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

}
