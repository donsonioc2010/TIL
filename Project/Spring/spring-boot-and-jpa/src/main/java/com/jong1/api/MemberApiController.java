package com.jong1.api;

import com.jong1.domain.Address;
import com.jong1.domain.Member;
import com.jong1.service.MemberService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberApiController {
    private final MemberService memberService;

    @PostMapping("/api/v1/members")
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member) {
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @PostMapping("/api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request) {
        Member member = request.toEntity();
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @PutMapping("/api/v2/members/{id}")
    public UpdateMemberResponse updateMemberV2(
            @PathVariable("id") Long id,
            @RequestBody @Valid UpdateMemberRequest request
    ) {
        memberService.update(id, request.getName(), request.getAddress());
        Member findMember = memberService.findOne(id);
        return UpdateMemberResponse.builder()
                .id(findMember.getId())
                .name(findMember.getName())
                .build();
    }

    @Data
    @Builder
    @AllArgsConstructor
    static class UpdateMemberResponse {
        private Long id;
        private String name;

    }

    @Data
    @AllArgsConstructor
    static class UpdateMemberRequest {
        private String name;
        private Address address;
    }

    @Data
    static class CreateMemberRequest {
        @NotEmpty
        private String name;
        private Address address;

        Member toEntity() {
            return Member.builder()
                    .name(name)
                    .address(address)
                    .build();
        }
    }

    @Data
    static class CreateMemberResponse {
        private Long id;

        public CreateMemberResponse(Long id) {
            this.id = id;
        }

    }
}
