package com.jong1.datajpa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = PROTECTED)
public class Member {
    @Id
    @GeneratedValue
    private Long id;

    private String username;

    @Builder
    public Member(String username) {
        this.username = username;
    }
}
