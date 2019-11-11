package com.example.blog.controller;

import com.example.blog.service.BlogService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.inject.Inject;
import java.util.Map;

@Controller
public class BlogController {

    BlogService blogService;

    @Inject
    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @GetMapping("/blog")
    public void getBlogList(Map<String, String> map) {

    }
}
