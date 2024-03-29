package com.example.querydsl.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberDto {
    private String username;
    private int age;

    @Builder
    public MemberDto(String username, int age) {
        this.username = username;
        this.age = age;
    }
}
