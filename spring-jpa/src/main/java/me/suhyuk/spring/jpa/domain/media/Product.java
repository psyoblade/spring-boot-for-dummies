package me.suhyuk.spring.jpa.domain.media;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@AllArgsConstructor
@NoArgsConstructor
@ToString
public abstract class Product {
    @Id @GeneratedValue
    @Column(name = "PRODUCT_ID")
    private Long id;
    private String name;
    private Integer price;
}
