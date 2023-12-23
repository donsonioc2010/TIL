package com.jong1.controller;

import com.jong1.domain.Address;
import com.jong1.domain.Member;
import com.jong1.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    /**
     * 회원 목록 조회
     * @param model
     * @return
     */
    @GetMapping
    public String list(Model model) {
        model.addAttribute("members", memberService.findMembers());
        return "members/memberList";
    }

    /**
     * 회원가입 폼으로 이동
     */
    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    /**
     * 회원가입 기능 실행
     * @param form
     * @param result
     * @return
     */
    @PostMapping("/new")
    public String create(@Valid MemberForm form, BindingResult result) {
        if(result.hasErrors()) {
            return "members/createMemberForm";
        }

        Address address = Address.builder()
                .city(form.getCity())
                .street(form.getStreet())
                .zipcode(form.getZipcode())
                .build();

        Member member = Member.builder()
                .name(form.getName())
                .address(address)
                .build();

        memberService.join(member);
        return "redirect:/";
    }
}
