package me.suhyuk.spring.jpa.domain.order;

import lombok.*;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "MEMBER")
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id @GeneratedValue()
    @Column(name = "MEMBER_ID")
    private Long id;
    private String name;
    @Embedded
    @AttributeOverrides( {
            @AttributeOverride(name = "city", column = @Column(name = "HOME_ADDR_CITY")),
            @AttributeOverride(name = "street", column = @Column(name = "HOME_ADDR_STREET")),
            @AttributeOverride(name = "zipCode", column = @Column(name = "HOME_ADDR_ZIPCODE"))
    })
    private Address homeAddress;
    @Embedded
    @AttributeOverrides( {
            @AttributeOverride(name = "city", column = @Column(name = "OFFICE_ADDR_CITY")),
            @AttributeOverride(name = "street", column = @Column(name = "OFFICE_ADDR_STREET")),
            @AttributeOverride(name = "zipCode", column = @Column(name = "OFFICE_ADDR_ZIPCODE"))
    })
    private Address officeAddress;

    @ElementCollection
    @CollectionTable(name = "FAVORITE_FOOD", joinColumns =
            @JoinColumn(name = "MEMBER_ID")
    )
    private Set<String> favoriteFoods = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "ADDRESS", joinColumns =
            @JoinColumn(name = "MEMBER_ID")
    )
    private List<Address> addressHistory = new ArrayList<>();

    @Builder
    public Member(Long id, String name, Address homeAddress, Address officeAddress, Set<String> favoriteFoods, List<Address> addressHistory) {
        this.id = id;
        this.name = name;
        this.homeAddress = homeAddress;
        this.officeAddress = officeAddress;
        this.favoriteFoods = favoriteFoods;
        this.addressHistory = addressHistory;
    }

    public void doOrder(Order order) {
        order.selectMember(this);
    }

    public Member deepCopy(Long newId, Member member) {
        return Member.builder()
                .id(newId)
                .name(member.getName())
                .homeAddress(member.getHomeAddress())
                .officeAddress(member.getOfficeAddress())
                .favoriteFoods(member.getFavoriteFoods())
                .addressHistory(member.getAddressHistory())
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return Objects.equals(getId(), member.getId()) && Objects.equals(getName(), member.getName()) && Objects.equals(getHomeAddress(), member.getHomeAddress());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getHomeAddress());
    }
}
