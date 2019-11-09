package com.example.blog.service;

import com.example.blog.mapper.UserMapper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserService implements UserDetailsService {
    private BCryptPasswordEncoder cryptPasswordEncoder;
    private UserMapper userMapper;
    private Map<String, com.example.blog.model.User> users = new ConcurrentHashMap<>();

    @Inject
    public UserService(BCryptPasswordEncoder cryptPasswordEncoder,UserMapper userMapper) {
        this.cryptPasswordEncoder = cryptPasswordEncoder;
        this.userMapper=userMapper;
    }


    public com.example.blog.model.User getUserByUsername(String username) {
        return userMapper.getUserByName(username);
    }

    /**
     * 保存用户到数据库
     * @param username
     * @param password
     */
    public void save(String username, String password) {
        com.example.blog.model.User user = new com.example.blog.model.User( username, cryptPasswordEncoder.encode(password));
        userMapper.save(user);
    }

    /**
     * 加载用户信息
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.example.blog.model.User user=userMapper.getUserByName(username);
        if (user==null) {
            throw new UsernameNotFoundException(username + "用户名不存在");
        }
        String encodedPassword = user.getEncryptedPassword();
        return new User(username, encodedPassword, Collections.emptyList());
//        return User.builder()
//                .passwordEncoder(cryptPasswordEncoder::new)
//                .username(username)
//                .password(encodedPassword)
//                .roles("USER")
//                .build();
    }
}
