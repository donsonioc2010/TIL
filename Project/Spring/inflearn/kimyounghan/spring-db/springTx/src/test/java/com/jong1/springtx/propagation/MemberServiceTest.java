package com.jong1.springtx.propagation;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class MemberServiceTest {

    @Autowired private MemberService memberService;
    @Autowired private MemberRepository memberRepository;
    @Autowired private LogRepository logRepository;

    /**
     * memberService @Transactional : X
     * memberRepository @Transactional : O
     * logRepository @Transactional : O
     */
    @Test
    void outerTxOff_success() {
        //given
        String username = "outerTxOff_success";

        //when
        memberService.joinV1(username);

        //then
        Assertions.assertTrue(memberRepository.find(username).isPresent());
        Assertions.assertTrue(logRepository.find(username).isPresent());
    }

    /**
     * memberService @Transactional : X
     * memberRepository @Transactional : O
     * logRepository @Transactional : O EXCEPTION
     */
    @Test
    void outerTxOff_fail() {
        //given
        String username = "로그예외";

        //when
        Assertions.assertThrows(RuntimeException.class, () -> memberService.joinV1(username));

        //then
        Assertions.assertTrue(memberRepository.find(username).isPresent());
        Assertions.assertTrue(logRepository.find(username).isEmpty());
    }
}