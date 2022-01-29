package me.suhyuk.spring.jpa.domain.order;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "ORDER_ITEM")
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

    @Id
    @GeneratedValue
    @Column(name = "ORDER_ITEM_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ORDER_ID")
    private Order order;

    @JoinColumn(name = "ITEM_ID")
    @ManyToOne
    private Item item;

    @Column(name = "ORDER_PRICE")
    private int orderPrice;
    private int count;

    @Builder
    public OrderItem(int count, Item item) {
        this.count = count;
        this.item = item;
        this.orderPrice = this.count * item.getPrice();
    }

    public void registerOrder(Order order) {
        this.order = order;
    }
}
