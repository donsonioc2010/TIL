package com.jong1.datajpa.dto;

import com.jong1.datajpa.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberDto {
    private Long id;
    private String username;
    private String teamName;

    public MemberDto(Member m){
        this.id = m.getId();
        this.username = m.getUsername();
    }
}
