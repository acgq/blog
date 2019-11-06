package com.example.blog.model;

import java.time.Instant;

public class User {
    private int id;
    private String name;
    private String avatar;
    private Instant createAt;
    private Instant updateAt;

    public User() {
    }

    public User(int id, String name, String avatar) {
        this.id = id;
        this.name = name;
        this.avatar = avatar;
        this.createAt=Instant.now();
        this.updateAt=Instant.now();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAvatar() {
        return avatar;
    }

    public Instant getCreateAt() {
        return createAt;
    }

    public Instant getUpdateAt() {
        return updateAt;
    }
}

