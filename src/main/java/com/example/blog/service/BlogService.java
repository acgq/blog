package com.example.blog.service;

import com.example.blog.dao.BlogDao;
import com.example.blog.model.Blog;
import com.example.blog.model.BlogListResult;
import com.example.blog.model.BlogResult;
import com.example.blog.model.User;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Objects;

@Service
public class BlogService {
    BlogDao blogDao;

    @Inject
    public BlogService(BlogDao blogDao) {
        this.blogDao = blogDao;
    }

    public BlogListResult getBlog(Integer page, Integer pageSize, Integer userId) {

        try {
            List<Blog> list = blogDao.selectBlog(page, pageSize, userId);
            int count = blogDao.countBlog(userId);
            int pageCount = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;
            return BlogListResult.success(list, count, page, count);
        } catch (Exception e) {
            return BlogListResult.fail("系统异常");
        }
    }

    public BlogResult getBlogById(Integer blogId) {
        try {
            return BlogResult.success("获取成功", blogDao.selectBlogById(blogId));
        } catch (Exception e) {
            return BlogResult.fail(e.getMessage());
        }
    }

    public BlogResult createBlog(Blog blog) {

        try {
            return BlogResult.success("创建成功", blogDao.insertBlog(blog));
        } catch (Exception e) {
            return BlogResult.fail("系统异常");
        }

    }

    public BlogResult updateBlog(Integer blogId, Blog targetBlog) {
        Blog blogFromDB = blogDao.selectBlogById(blogId);
        if (blogFromDB == null) {
            return BlogResult.fail("博客不存在");
        }
        if (!Objects.equals(blogFromDB.getUser().getId(), targetBlog.getUser().getId())) {
            return BlogResult.fail("无法修改他人博客");
        }
        try {
            targetBlog.setId(blogId);
            Blog blog = blogDao.updateBlog(targetBlog);
            return BlogResult.success("修改成功", blog);
        } catch (Exception e) {
            return BlogResult.fail(e.getMessage());
        }
    }

    public BlogResult deleteBlog(int blogId, User user) {
        Blog blogFromDB = blogDao.selectBlogById(blogId);
        if (blogFromDB == null) {
            return BlogResult.fail("博客不存在");
        }
        if (!Objects.equals(blogFromDB.getUserId(), user.getId())) {
            return BlogResult.fail("无法修改他人博客");
        }
        try {
            blogDao.deleteBlog(blogId);
            return BlogResult.success("修改成功");
        } catch (Exception e) {
            return BlogResult.fail(e.getMessage());
        }
    }
}
