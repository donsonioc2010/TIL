package com.jong1.jdbc.service;

import com.jong1.jdbc.domain.Member;
import com.jong1.jdbc.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

/**
 * 트랜잭션 - @Transactional AOP
 * @Transactional 어노테이션이 달리면 스프링이 Load를 할 때부터 Aop Proxy로 생성을 한다.
 */
@Slf4j
@RequiredArgsConstructor
public class MemberServiceV4 {

    private final MemberRepository memberRepository;

    @Transactional
    public void accountTransfer(String fromId, String toId, int money) {
        businessLogic(fromId, toId, money);
    }

    private void businessLogic(String fromId, String toId, int money) {
        Member fromMember = memberRepository.findById(fromId);
        Member toMember = memberRepository.findById(toId);

        memberRepository.update(fromId, fromMember.getMoney() - money);
        validation(toMember);
        memberRepository.update(toId, toMember.getMoney() + money);
    }

    private void validation(Member toMember) {
        if(toMember.getMemberId().equals("ex")) {
            throw new IllegalStateException("이채중 예외 발생");
        }
    }

}
