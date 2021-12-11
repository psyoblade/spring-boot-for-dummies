package me.suhyuk.springjpa.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
public class Member {

    @Id @GeneratedValue()
    @Column(name = "member_id")
    private long id;
    private String name;
    private String city;
    private String street;
    private String zipcode;

}
