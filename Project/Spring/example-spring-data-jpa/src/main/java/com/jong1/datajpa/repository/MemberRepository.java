package com.jong1.datajpa.repository;


import com.jong1.datajpa.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

    /**
     * limit는 Spring Data JPA Docs확인
     * @return
     */
    List<Member> findTop3By();
}
