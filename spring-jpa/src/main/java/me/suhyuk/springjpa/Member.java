package me.suhyuk.springjpa;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "MEMBER" , uniqueConstraints = { @UniqueConstraint( columnNames = { "userName", "age" })} )
@Entity
public class Member {

    @Id
    private long id;

    @Column(nullable = false)
    private String userName;

    private int age;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    private LocalDate xCreateDate;
    private LocalDateTime xCreateDateTime;

    @Lob
    private String description;

    @Transient
    private int foo;

    @Builder
    public Member(long id, String userName, RoleType roleType) {
        this.id = id;
        this.userName = userName;
        this.roleType = roleType;
    }

    enum RoleType {
        USER, ADMIN
    }

}
