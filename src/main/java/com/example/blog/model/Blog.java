package com.example.blog.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.Instant;

public class Blog {
    private int id;
    @JsonIgnore
    private int userId;
    private String title;
    private String description;
    private String content;
    private User user;
    private Instant createAt;
    private Instant updateAt;

    public Blog() {
    }

    public Blog(String title, String description, String content, User user) {
        this.title = title;
        this.description = description;
        this.content = content;
        this.user = user;
    }


    @Override
    public String toString() {
        return "Blog{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", user=" + user +
                ", createAt=" + createAt +
                ", updateAt=" + updateAt +
                '}';
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public User getUser() {
        return user;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setCreateAt(Instant createAt) {
        this.createAt = createAt;
    }

    public void setUpdateAt(Instant updateAt) {
        this.updateAt = updateAt;
    }

    public Instant getCreateAt() {
        return createAt;
    }

    public Instant getUpdateAt() {
        return updateAt;
    }
}
