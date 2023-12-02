package com.markiyanova.asianhouse.entity.user;

import com.markiyanova.asianhouse.entity.order.OrderEntity;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "basket")
public class BasketEntity {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;


    @Getter
    @Setter
    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Getter
    @Setter
    @OneToMany(mappedBy = "basket", cascade = CascadeType.ALL)
    private List<BasketItemEntity> basketItems;


    @Getter
    @Setter
    @OneToMany(mappedBy = "basket", cascade = CascadeType.ALL)
    private List<OrderEntity> orderEntities;

    public BasketEntity(){};



}
