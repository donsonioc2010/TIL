package com.example.querydsl.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberDto2 {
    private String username;
    private int age;

    /**
     * @QueryProjection을 사용하면 QMemberDto2를 생성할 필요가 없다.
     * 또한 Build시 Q파일이 생성된다.
     * @param username
     * @param age
     */
    @QueryProjection
    public MemberDto2(String username, int age) {
        this.username = username;
        this.age = age;
    }
}
