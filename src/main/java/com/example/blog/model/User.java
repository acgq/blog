package com.example.blog.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.Instant;

public class User {
    private int id;
    private String name;
    @JsonIgnore
    private String encryptedPassword;
    private String avatar;
    private Instant createAt;
    private Instant updateAt;

    public User() {
    }

    public User( String name, String encryptedPassword, String avatar) {
        this.name = name;
        this.encryptedPassword = encryptedPassword;
        this.avatar = avatar;
        this.createAt=Instant.now();
        this.updateAt=Instant.now();
    }

    public User(String name, String encryptedPassword) {
        this(name,encryptedPassword,null);
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
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

