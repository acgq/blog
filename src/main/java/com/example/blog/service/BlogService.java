package com.example.blog.service;

import com.example.blog.dao.BlogDao;
import com.example.blog.model.Blog;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class BlogService {
    BlogDao blogDao;

    @Inject
    public BlogService(BlogDao blogDao) {
        this.blogDao = blogDao;
    }

    public List<Blog> getBlogs(int page, int userId) {
        List<Blog> list = blogDao.getBlogs(page, userId);
        return list;
    }
}
