package com.example.blog.service;

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
    private Map<String, String> usernameAndPassword = new ConcurrentHashMap<>();

    @Inject
    public UserService(BCryptPasswordEncoder cryptPasswordEncoder) {
        this.cryptPasswordEncoder = cryptPasswordEncoder;
        usernameAndPassword.put("chen", cryptPasswordEncoder.encode("123654"));
    }


    private String getPassword(String username) {
        return usernameAndPassword.get(username);
    }

    public void save(String username, String password) {
        usernameAndPassword.put(username, cryptPasswordEncoder.encode(password));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (!usernameAndPassword.containsKey(username)) {
            throw new UsernameNotFoundException(username + "用户名不存在");
        }
        String encodedPassword = usernameAndPassword.get(username);
        return new User(username,encodedPassword,Collections.emptyList());
//        return User.builder()
//                .passwordEncoder(cryptPasswordEncoder::new)
//                .username(username)
//                .password(encodedPassword)
//                .roles("USER")
//                .build();
    }
}
