package me.suhyuk.springjpa.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
@ToString
public class Member {

    @Id @GeneratedValue()
    @Column(name = "member_id")
    private Long id;
    private String name;
    private String city;
    private String street;
    private String zipCode;

    @Builder
    public Member(Long id, String name, String city, String street, String zipCode) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.street = street;
        this.zipCode = zipCode;
    }

    public Member deepCopy(Long newId, Member member) {
        return Member.builder().id(newId).name(member.getName()).city(member.getCity()).street(member.getStreet()).zipCode(member.getZipCode()).build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return Objects.equals(id, member.id) && Objects.equals(name, member.name) && Objects.equals(city, member.city) && Objects.equals(street, member.street) && Objects.equals(zipCode, member.zipCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, city, street, zipCode);
    }
}
