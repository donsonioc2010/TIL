package com.example.querydsl.repository;

import com.example.querydsl.dto.MemberSearchCondition;
import com.example.querydsl.dto.MemberTeamDto;

import java.util.List;

/**
 * 명칭은 꼭 Custom으로 끝나지 않아도 된다.
 *
 * 꼭 상속받지 않고, 따로 구현해도 상관은 없음
 */
public interface MemberRepositoryCustom {
    List<MemberTeamDto> search(MemberSearchCondition condition);
}
