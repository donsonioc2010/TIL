package com.jong1.datajpa.repository;

public class UsernameOnlyDto {
    private final String username;

    // 생성자의 파라미터명과 프로퍼티명이 같으면 @Value를 사용하지 않아도 된다
    // 생성자의 파라미터 명칭을 바탕으로 매칭하여 값을 주입한다
    public UsernameOnlyDto(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
