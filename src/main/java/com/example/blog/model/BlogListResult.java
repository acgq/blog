package com.example.blog.model;

import java.util.List;

public class BlogListResult extends Result<List<Blog>> {
    private int total;
    private int page;
    private int totalPage;


    protected BlogListResult(String status, String msg, List<Blog> blogList, int total, int page, int totalPage) {
        super(status, msg, blogList);
        this.page = page;
        this.total = total;
        this.totalPage = totalPage;
    }

    public static BlogListResult fail(String msg) {
        return new BlogListResult("fail", msg, null, 0, 0, 0);
    }

    public static BlogListResult success(List<Blog> blogList, int total, int page, int totalPage) {
        return new BlogListResult("ok", "获取成功", blogList, total, page, totalPage);
    }
}
