package com.jong1.domain.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@DiscriminatorValue("A")
@Entity
public class Album extends Item{
    private String artist;
    private String etc;
}
