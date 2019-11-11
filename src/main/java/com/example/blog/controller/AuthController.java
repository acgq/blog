package com.example.blog.controller;

import com.example.blog.model.LoginResult;
import com.example.blog.model.Result;
import com.example.blog.model.User;
import com.example.blog.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.management.openmbean.KeyAlreadyExistsException;
import java.util.Map;
import java.util.regex.Pattern;

@RestController
public class AuthController {

    private UserService userService;
    private AuthenticationManager authenticationManager;
    private Pattern pattern = Pattern.compile("[a-zA-Z_0-9\\u4e00-\\u9fa5]*");

    @Inject
    public AuthController(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @GetMapping(value = "/auth"/*,produces={"application/json; charset=UTF-8"}*/)
    @ResponseBody
    public Object auth() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User loggedUser = authentication == null ? null : userService.getUserByUsername(authentication.getName());
        if (loggedUser == null) {
            return LoginResult.fail("用户未登录");
        }
        return LoginResult.success("用户已登录", loggedUser);
    }

    @PostMapping("/auth/login")
    @ResponseBody
    public Result login(@RequestBody Map<String, String> usernameAndPassword) {
        final String username = usernameAndPassword.get("username");
        final String password = usernameAndPassword.get("password");
        //验证用户
        UserDetails userDetails = null;
        try {
            userDetails = userService.loadUserByUsername(username);
        } catch (UsernameNotFoundException e) {
            return LoginResult.fail("用户不存在");
        }
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
        try {
            authenticationManager.authenticate(token);
            //保存用户信息
            SecurityContextHolder.getContext().setAuthentication(token);
        } catch (AuthenticationException e) {
            return LoginResult.fail("密码不正确");
        }
        User user = userService.getUserByUsername(username);
        return LoginResult.success("登录成功", user);
    }

    @PostMapping("/auth/register")
    @ResponseBody
    public Result register(@RequestBody Map<String, String> usernameAndPassword) {
        String username = usernameAndPassword.get("username");
        String password = usernameAndPassword.get("password");
        //输入格式合法性判断
        if (username == null || password == null) {
            return LoginResult.fail("用户名或密码为空");
        }
        if (!isUsernameValid(username)) {
            return LoginResult.fail("用户名不合法");
        }
        if (!isPasswordValid(password)) {
            return LoginResult.fail("密码不合法");
        }
        User user = userService.getUserByUsername(username);
        if (user == null) {
            try {
                userService.save(username, password);
            } catch (KeyAlreadyExistsException e) {
                return LoginResult.fail("用户已存在");
            }
            User registeredUser = userService.getUserByUsername(username);
            return LoginResult.success("注册成功", registeredUser);
        } else {
            return LoginResult.fail("用户已存在");
        }
    }

    @GetMapping("/auth/logout")
    @ResponseBody
    public Result logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication == null ? null : authentication.getName();
        if (name == null || name == "") {
            return LoginResult.fail("用户尚未登录");
        }
        SecurityContextHolder.clearContext();
        return LoginResult.fail("注销成功");
    }

    private boolean isPasswordValid(String password) {
        return password.length() <= 16 & password.length() >= 6;
    }

    private boolean isUsernameValid(String username) {
        return username.length() > 0 && username.length() < 16 && pattern.matcher(username).matches();
    }


}
