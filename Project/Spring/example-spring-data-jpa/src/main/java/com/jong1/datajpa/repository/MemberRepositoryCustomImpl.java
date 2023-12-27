package com.jong1.datajpa.repository;

import com.jong1.datajpa.entity.Member;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * QueryDSL이라든지, MyBatis를 사용할 때라든지 등등..
 * Naming규칙은 Repository명 + Impl로 하면 Spring Data JPA가 인식한다. (MemberRepositoryImpl, MemberRepositoryCustomImpl)
 */
@RequiredArgsConstructor
public class MemberRepositoryCustomImpl implements MemberRepositoryCustom {
    private final EntityManager em;
    @Override
    public List<Member> findMemberCustom() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }
}
