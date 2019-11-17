package com.example.blog.controller;

import com.example.blog.service.AuthService;
import com.example.blog.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.servlet.http.HttpSession;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Disabled
@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class AuthControllerTest {
    private MockMvc mvc;
    @Mock
    private UserService userService;
    @Mock
    private AuthenticationManager authenticationManager;
    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(new AuthController(userService, authenticationManager, authService)).build();
    }


    @Test
    void auth() throws Exception {
        Mockito.lenient().when(userService.loadUserByUsername("myUser")).thenReturn(new User("myUser", "password", Collections.emptyList()));
        Mockito.lenient().when(userService.getUserByUsername("myUser")).thenReturn(new com.example.blog.model.User("myUser", "password"));
        //未登录
        mvc.perform(get("/auth"))
                .andExpect(status().isOk())
                .andExpect(result -> Assertions.assertTrue(result.getResponse().getContentAsString(Charset.defaultCharset()).contains("用户未登录")));
        mvc.perform(get("/auth/logout"))
                .andExpect(result -> Assertions.assertTrue(result.getResponse().getContentAsString(Charset.defaultCharset()).contains("用户尚未登录")));

        //登录
        Map<String, String> map = new HashMap<>();
        map.put("password", "password");
        map.put("username", "myUser");
        String postJson = new ObjectMapper().writeValueAsString(map);
        final MvcResult mvcResult = mvc.perform(post("/auth/login").contentType(MediaType.APPLICATION_JSON).content(postJson)).andReturn();
        Assertions.assertTrue(mvcResult.getResponse().getContentAsString(Charset.defaultCharset()).contains("登录成功"));
        //已登录
        HttpSession session = mvcResult.getRequest().getSession();
        mvc.perform(get("/auth").session((MockHttpSession) session))
                .andExpect(status().isOk())
                .andExpect(result -> Assertions.assertTrue(result.getResponse().getContentAsString(Charset.defaultCharset()).contains("用户已登录")));
        //注销
        mvc.perform(get("/auth/logout").session((MockHttpSession) session))
                .andExpect(result -> Assertions.assertTrue(result.getResponse().getContentAsString(Charset.defaultCharset()).contains("注销成功")));
    }

    @Test
    void login() throws Exception {
        //登录
        Mockito.lenient().when(userService.loadUserByUsername("myUser")).thenReturn(new User("myUser", "password", Collections.emptyList()));
        Mockito.lenient().when(userService.getUserByUsername("myUser")).thenReturn(new com.example.blog.model.User("myUser", "password"));
        Mockito.lenient().when(userService.loadUserByUsername("fakeUser")).thenThrow(UsernameNotFoundException.class);

        Map<String, String> map = new HashMap<>();
        map.put("password", "password");
        map.put("username", "myUser");
        String postJson = new ObjectMapper().writeValueAsString(map);
        MvcResult mvcResult = mvc.perform(post("/auth/login").contentType(MediaType.APPLICATION_JSON).content(postJson)).andReturn();
        Assertions.assertTrue(mvcResult.getResponse().getContentAsString(Charset.defaultCharset()).contains("登录成功"));
        map.put("username", "fakeUser");
        postJson = new ObjectMapper().writeValueAsString(map);
        mvc.perform(post("/auth/login").contentType(MediaType.APPLICATION_JSON).content(postJson))
                .andExpect(result -> Assertions.assertTrue(result.getResponse().getContentAsString(Charset.defaultCharset()).contains("用户不存在")));
    }

    @Test
    void register() throws Exception {
        Mockito.lenient().when(userService.getUserByUsername("myUser")).thenReturn(new com.example.blog.model.User("myUser", "password"));
        Mockito.lenient().when(userService.loadUserByUsername("fakeUser")).thenReturn(new User("fakeUser", "password", Collections.emptyList()));

        Map<String, String> map = new HashMap<>();
        map.put("password", "password");
        map.put("username", "myUser");
        String postJson = new ObjectMapper().writeValueAsString(map);
        mvc.perform(post("/auth/register").contentType(MediaType.APPLICATION_JSON).content(postJson).content(postJson))
                .andExpect(result -> Assertions.assertTrue(result.getResponse().getContentAsString(Charset.defaultCharset()).contains("用户已存在")));
        map.put("username", "fakeUser");
        postJson = new ObjectMapper().writeValueAsString(map);
        mvc.perform(post("/auth/register").contentType(MediaType.APPLICATION_JSON).content(postJson).content(postJson))
                .andExpect(result -> Assertions.assertTrue(result.getResponse().getContentAsString(Charset.defaultCharset()).contains("注册成功")));
        mvc.perform(get("/auth/logout"));
    }
}
