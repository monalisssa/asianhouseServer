package com.markiyanova.asianhouse.entity.order;


import com.markiyanova.asianhouse.entity.menu.MenuItemEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "order_item")
public class OrderItemEntity {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    public OrderItemEntity(){};


    @Getter
    @Setter
    @OneToOne
    @JoinColumn(name = "menu_item_id")
    private MenuItemEntity menuItem;


    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderEntity order;

    @Getter
    @Setter
    private int quantity;
}
