package me.suhyuk.spring.jpa.domain.media;

import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@NoArgsConstructor
public class Book extends Product {
    private String author;
    private String isbn;
    @Builder
    public Book(Long id, String name, Integer price, String author, String isbn) {
        super(id, name, price);
        this.author = author;
        this.isbn = isbn;
    }
}
