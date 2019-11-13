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
        blogService.getBlog(1, 10, 1);
        Mockito.verify(blogDao).selectBlog(1, 10, 1);
    }
}