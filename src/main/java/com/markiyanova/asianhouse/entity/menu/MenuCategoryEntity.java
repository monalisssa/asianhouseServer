package com.markiyanova.asianhouse.entity.menu;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "menu_category")
public class MenuCategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter
    @Getter
    @Column(name = "id")
    private long id;

    public MenuCategoryEntity(){};

    @Getter
    @Setter
    @OneToMany(mappedBy = "menuCategory", cascade = CascadeType.ALL)
    private List<MenuItemEntity> menuItems;

    @Getter
    @Setter
    @Column(name = "name")
    private String name;


    @Getter
    @Setter
    @Lob
    @Column(name = "image", columnDefinition="longblob")
    private byte[] image;

}
