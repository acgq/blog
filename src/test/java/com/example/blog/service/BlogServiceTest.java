package com.example.blog.service;

import com.example.blog.dao.BlogDao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BlogServiceTest {
    @Mock
    private BlogDao blogDao;

    @InjectMocks
    private BlogService blogService;

    @Test
    void getBiogs() {
        blogService.getBlogs(1, 1);
        Mockito.verify(blogDao).getBlogs(1, 1);
    }
}