package com.example.blog.model;

import java.util.List;

public class BlogResult extends Result<List<Blog>> {
    private int total;
    private int page;
    private int totalPage;


    protected BlogResult(String status, String msg, List<Blog> blogList) {
        super(status, msg, blogList);
    }

    public static BlogResult fail(String msg) {
        return new BlogResult("fail", msg, null);
    }
    public static BlogResult success(String msg,List<Blog> blogList){
        return new BlogResult("ok",msg,blogList);
    }
    public static BlogResult success(String msg){
        return new BlogResult("ok",msg,null);
    }
}
