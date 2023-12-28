package com.jong1.datajpa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;

import static lombok.AccessLevel.PROTECTED;

// Persistable을 구현하여 식별자가 null인지 아닌지를 판단할 수 있도록 구현
@Entity
@NoArgsConstructor(access = PROTECTED)
public class Item extends BaseTimeEntity implements Persistable<String> {
    @Id
    private String id;

    @Builder
    public Item(String id) {
        this.id = id;
    }


    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public boolean isNew() {
        return getCreatedDate() == null;
    }
}
