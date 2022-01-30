package me.suhyuk.spring.jpa.domain.media;

import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;

@Entity
@NoArgsConstructor
@ToString(callSuper = true)
public class Album extends Product {
    private String artist;

    @Builder
    public Album(Long id, String name, Integer price, String artist) {
        super(id, name, price);
        this.artist = artist;
    }
}

