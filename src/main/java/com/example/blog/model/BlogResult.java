package com.example.blog.model;

public class BlogResult extends Result<Blog> {
    private int total;
    private int page;
    private int totalPage;


    protected BlogResult(String status, String msg, Blog blog) {
        super(status, msg, blog);
    }

    public static BlogResult fail(String msg) {
        return new BlogResult("fail", msg, null);
    }

    public static BlogResult success(String msg, Blog blog) {
        return new BlogResult("ok", msg, blog);
    }

    public static BlogResult success(String msg) {
        return new BlogResult("ok", msg, null);
    }
}
