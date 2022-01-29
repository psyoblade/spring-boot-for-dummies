package me.suhyuk.spring.jpa.domain.order;

import javax.persistence.*;

@Entity
@Table(name = "DELIVERY")
public class Delivery {

    @Id @GeneratedValue
    @Column(name = "DELIVERY_ID")
    private Long id;

    private String name;
    private String city;
    private String street;
    private String zipCode;
    private DeliveryStatus deliveryStatus;

    @OneToOne(mappedBy = "delivery")
    private Order order;
}
