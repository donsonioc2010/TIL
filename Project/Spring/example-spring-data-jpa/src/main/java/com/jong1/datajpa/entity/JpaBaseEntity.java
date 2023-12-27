package com.jong1.datajpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * JPA Auditing
 */
@Getter
@MappedSuperclass // 상속관계 매핑시 사용
public class JpaBaseEntity {
    @Column(updatable = false) // 수정 변경 금지
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    @PrePersist // persist하기 전에 실행
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        this.createdDate = now;
        this.updatedDate = now;
    }

    @PreUpdate // update하기 전에 실행
    public void preUpdate() {
        this.updatedDate = LocalDateTime.now();
    }
}
