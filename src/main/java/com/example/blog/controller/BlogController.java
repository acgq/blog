package com.example.blog.controller;

import com.example.blog.model.Blog;
import com.example.blog.model.BlogListResult;
import com.example.blog.model.BlogResult;
import com.example.blog.model.User;
import com.example.blog.service.AuthService;
import com.example.blog.service.BlogService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.Map;
import java.util.Optional;

@Controller
public class BlogController {

    private AuthService authService;
    private BlogService blogService;

    @Inject
    public BlogController(BlogService blogService, AuthService authService) {
        this.blogService = blogService;
        this.authService = authService;
    }

    @GetMapping("/blog")
    @ResponseBody
    public BlogListResult getBlogList(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                      @RequestParam(value = "userId", required = false) Integer userId,
                                      @RequestParam(value = "atIndex", required = false) boolean atIndex) {
        int pageSize = 10;
        BlogListResult blogListResult = blogService.getBlog(page, pageSize, userId);
        return blogListResult;
    }

    @GetMapping("/blog/{blogId}")
    @ResponseBody
    public BlogResult getBlogById(@PathVariable("blogId") Integer blogId) {
        BlogResult blogById = blogService.getBlogById(blogId);
        return blogById;
    }

    @PostMapping("/blog")
    @ResponseBody
    public BlogResult createBlog(@RequestBody Map<String, String> blogMap) {
        //验证用户
        Optional<User> currentUser = authService.getCurrentUser();
        try {
            if (currentUser.isEmpty()) {
                return BlogResult.fail("登录后才能操作");
            } else {
                User user = currentUser.get();
                Blog blog = formBlog(blogMap, user);
                return blogService.createBlog(blog);
            }
        } catch (Exception e) {
            return BlogResult.fail(e.getMessage());
        }
    }

    @PatchMapping("/blog/{blogId}")
    @ResponseBody
    public BlogResult updateBlog(@PathVariable("blogId") Integer blogId,
                                 @RequestBody Map<String, String> blogMap) {
        //验证用户
        try {
            return authService.getCurrentUser()
                    .map(user -> blogService.updateBlog(blogId, formBlog(blogMap, user)))
                    .orElse(BlogResult.fail("登录后才能操作"));
        } catch (Exception e) {
            return BlogResult.fail(e.getMessage());
        }
    }

    @DeleteMapping("/blog/{blogId}")
    @ResponseBody
    public BlogResult deleteBlog(@PathVariable("blogId") int blogId) {

        try {
            return authService
                    .getCurrentUser()
                    .map(user -> blogService.deleteBlog(blogId, user))
                    .orElse(BlogResult.fail("登录后才能操作"));
        } catch (Exception e) {
            return BlogResult.fail(e.getMessage());
        }
    }


    private Blog formBlog(@RequestBody Map<String, String> blogMap, User user) {
        String title = blogMap.get("title");
        String content = blogMap.get("content");
        String description = blogMap.get("description");
        if (StringUtils.isBlank(title) || title.length() > 10) {
            throw new IllegalArgumentException("标题格式不合法");
        }
        if (StringUtils.isBlank(content) || content.length() > 10000) {
            throw new IllegalArgumentException("内容格式不合法");
        }
        if (StringUtils.isBlank(description)) {
            if (content.length() > 10) {
                description = content.substring(0, 10) + "...";
            } else {
                description = content;
            }
        }
        //创建blog实例
        Blog blog = new Blog(title, description, content, user);
        blog.setUserId(user.getId());
        return blog;
    }
}
