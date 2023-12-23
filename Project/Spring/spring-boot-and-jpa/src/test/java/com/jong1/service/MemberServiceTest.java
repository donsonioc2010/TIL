package com.jong1.service;

import com.jong1.domain.Member;
import com.jong1.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void 회원가입() throws Exception {
        // given
        Member member = Member.builder().name("kim").build();

        // when
        Long saveId = memberService.join(member);

        // then
        assertEquals(member, memberRepository.findById(saveId));
    }

    @Test()
    public void 중복_회원_예외() throws Exception {
        // given
        Member member1 = Member.builder().name("kim1").build();
        Member member2 =Member.builder().name("kim1").build();

        // when
        memberService.join(member1);

        // then
        assertThrows(IllegalStateException.class, () -> memberService.join(member2));
    }
}