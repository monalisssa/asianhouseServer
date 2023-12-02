package com.markiyanova.asianhouse.entity.menu;

import com.markiyanova.asianhouse.entity.order.OrderItemEntity;
import com.markiyanova.asianhouse.entity.user.BasketItemEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.engine.jdbc.env.internal.LobTypes;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Blob;
import java.sql.Types;

@Entity
@Table(name = "menu_item")
public class MenuItemEntity {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    public MenuItemEntity(){};

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "menu_category_id")
    private MenuCategoryEntity menuCategory;

    @Getter
    @Setter
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "menu_item_info_id", referencedColumnName = "id")
    private MenuItemInfoEntity menuItemInfo;

    @Getter
    @Setter
    @Column(name = "name")
    private String name;

    @Getter
    @Setter
    @Column(name = "price")
    private double price;

    @Getter
    @Setter
    @Column(name = "weight")
    private int weight;

    @Getter
    @Setter
    @Lob
    @Column(name = "image", columnDefinition="longblob")
    private byte[] image;

}
