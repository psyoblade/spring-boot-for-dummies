package me.suhyuk.spring.jpa.domain.order;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "DELIVERY")
public class Delivery {

    @Id @GeneratedValue
    @Column(name = "DELIVERY_ID")
    private Long id;

    private String name;
    @Embedded
    private Address address;
    private DeliveryStatus deliveryStatus;

    @OneToOne(mappedBy = "delivery")
    private Order order;

}
