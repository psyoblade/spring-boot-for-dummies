package me.suhyuk.spring.jpa.domain.foo;

import javax.persistence.*;

@Entity
@Table(name = NewEntity.TABLE_NAME)
public class NewEntity {
    public static final String TABLE_NAME = "NEW_ENTITY";
    public static final String COLUMN_ID_NAME = "ID";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = COLUMN_ID_NAME, nullable = false)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}