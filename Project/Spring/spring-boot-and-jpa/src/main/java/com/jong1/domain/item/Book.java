package com.jong1.domain.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@DiscriminatorValue("B") // 필수 X
@Entity
public class Book extends Item{
    private String author;
    private String isbn;
}
