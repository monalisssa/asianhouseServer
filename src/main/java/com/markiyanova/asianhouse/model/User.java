package com.markiyanova.asianhouse.model;

import com.markiyanova.asianhouse.entity.user.UserEntity;

public class User {
    private long id;
    private String username;

    private String role;

    public static User toModel(UserEntity entity)
    {
         User model = new User();
         model.setId(entity.getId());
         model.setUsername(entity.getUsername());
         model.setRole(entity.getRole().getName());
         return model;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return this.role;
    }
}
