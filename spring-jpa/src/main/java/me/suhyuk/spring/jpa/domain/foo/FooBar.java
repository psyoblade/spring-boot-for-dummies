package me.suhyuk.spring.jpa.domain.foo;

import javax.persistence.*;

@Entity
@Table(name = "FOO_BAR")
public class FooBar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}