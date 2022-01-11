package me.suhyuk.spring.jpa.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity(name = "Foo")
@Table(name = "FOO")
public class Foo {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false)
    private String username;
    @Enumerated(EnumType.STRING)
    private RoleType roleType;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;
    @Column(precision = 19, scale = 2)
    private BigDecimal longValue;
    // LocalDate, LocalDateTime 은 @Temporal 이 필요없다
    private LocalDate insertedDate;
    private LocalDateTime insertedDateTime;
    @Lob
    private String description;
    public Foo(){
    }
}
