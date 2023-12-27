package com.jong1.datajpa.controller;

import com.jong1.datajpa.dto.MemberDto;
import com.jong1.datajpa.entity.Member;
import com.jong1.datajpa.repository.MemberRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberRepository memberRepository;

    @GetMapping("/members/{id}")
    public String findMember(@PathVariable("id") Long id) {
        Member member = memberRepository.findById(id).get();
        return member.getUsername();
    }

    // PK기반으로 web에서 넘어온 값을 바로 Entity로 받을 수 있으나, 권장은 하지 않는다
    @GetMapping("/members2/{id}")
    public String findMember2(@PathVariable("id") Member member) {
        return member.getUsername();
    }

    @GetMapping("/members")
    public Page<Member> list(Pageable pageable) {
        return memberRepository.findAll(pageable);
    }

    /**
     * 요청파라미터를 주지 않는 경우, 글로벌 설정을 무시한다
     * @param pageable
     * @return
     */
    @GetMapping("/members2")
    public Page<Member> list2(@PageableDefault(size=5) Pageable pageable) {
        return memberRepository.findAll(pageable);
    }


    @GetMapping("/members3")
    public Page<MemberDto> list3(Pageable pageable) {
        return memberRepository.findAll(pageable).map(MemberDto::new);
    }

    @PostConstruct
    public void init() {
        memberRepository.save(Member.builder().username("userA").build());
        for (int i = 0; i < 100; i++) {
            memberRepository.save(Member.builder().username("user" + i).build());
        }
    }
}
