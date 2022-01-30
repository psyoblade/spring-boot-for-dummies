package me.suhyuk.spring.jpa.domain.media;

import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@NoArgsConstructor
public class Movie extends Product {
    private String director;
    private String actor;
    @Builder
    public Movie(Long id, String name, Integer price, String director, String actor) {
        super(id, name, price);
        this.director = director;
        this.actor = actor;
    }
}
