package com.example.blog.model;

import java.time.Instant;

public class Blog {
    private int id;
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

    public Instant getCreateAt() {
        return createAt;
    }

    public Instant getUpdateAt() {
        return updateAt;
    }
}
