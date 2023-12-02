package com.markiyanova.asianhouse.entity.user;


import com.markiyanova.asianhouse.entity.menu.MenuItemEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "basket_item")
public class BasketItemEntity {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    public BasketItemEntity(){};

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "basket_id")
    private BasketEntity basket;

    @Getter
    @Setter
    @OneToOne
    @JoinColumn(name = "menu_item_id")
    private MenuItemEntity menuItem;

    @Getter
    @Setter
    private int quantity;
}
