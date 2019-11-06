package com.example.blog.controller;

import com.example.blog.model.User;
import com.example.blog.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.Map;

@RestController
public class AuthController {

    private UserDetailsService userDetailsService;

    private AuthenticationManager authenticationManager;

    @Inject
    public AuthController(UserService userDetailsService, AuthenticationManager authenticationManager) {
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
    }


    @GetMapping("/auth")
    @ResponseBody
    public Object auth() {
        return new Result("ok", null, true);
    }

    @PostMapping("/auth/login")
    @ResponseBody
    public Result login(@RequestBody Map<String, String> usernameAndPassword) {
        final String username = usernameAndPassword.get("username");
        final String password = usernameAndPassword.get("password");
        //验证用户
        UserDetails userDetails = null;
        try {
            userDetails = userDetailsService.loadUserByUsername(username);
        } catch (UsernameNotFoundException e) {
            return new Result("success", "用户不存在", false);
        }
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
        try {
            authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(token);
        } catch (AuthenticationException e) {
            return new Result("success", "密码不正确", false);
        }


        return new Result("ok", "登陆成功", true, new User(1, "chen", ""));
    }

    private static class Result {
        private String status;
        private String msg;
        private boolean isLogin;
        private Object data;

        public Result(String status, String msg, boolean isLogin) {
            this(status, msg, isLogin, null);
        }

        public Result(String status, String msg, boolean isLogin, Object object) {
            this.status = status;
            this.msg = msg;
            this.isLogin = isLogin;
            this.data = object;
        }

        public String getStatus() {
            return status;
        }

        public String getMsg() {
            return msg;
        }

        public boolean getIsLogin() {
            return isLogin;
        }

        public Object getData() {
            return data;
        }
    }
}
