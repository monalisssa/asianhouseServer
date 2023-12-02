package com.markiyanova.asianhouse.entity.user;

import com.markiyanova.asianhouse.entity.menu.MenuItemEntity;
import com.markiyanova.asianhouse.entity.user.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "user_info")
public class UserInfoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    public UserInfoEntity(){};

    @Getter
    @OneToOne(mappedBy = "userInfo", cascade = CascadeType.ALL)
    private UserEntity user;


    @Getter
    @Setter
    private String email;

    @Getter
    @Setter
    private String tel;

    @Getter
    @Setter
    private String firstname;

    @Getter
    @Setter
    private String lastname;

    @Getter
    @Setter
    private Date birth_date;

}
