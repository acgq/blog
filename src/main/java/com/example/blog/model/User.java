package com.example.blog.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.Instant;

public class User {
    private int id;
    private String username;
    @JsonIgnore
    private String encryptedPassword;
    private String avatar;
    private Instant createAt;
    private Instant updateAt;

    public User() {
    }

    public User(String username, String encryptedPassword, String avatar) {
        this.username = username;
        this.encryptedPassword = encryptedPassword;
        this.avatar = "https://blog-server.hunger-valley.com/avatar/69.jpg";
        this.createAt = Instant.now();
        this.updateAt = Instant.now();
    }

    public User(String username, String encryptedPassword) {
        this(username, encryptedPassword, null);
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setUpdateAt(Instant updateAt) {
        this.updateAt = updateAt;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
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

