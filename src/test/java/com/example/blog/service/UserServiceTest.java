package com.example.blog.service;

import com.example.blog.dao.UserMapper;
import com.example.blog.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    BCryptPasswordEncoder mockEncoder;
    @Mock
    UserMapper mockMapper;
    @InjectMocks
    UserService userService;

    @Test
    public void testSave() {
        //given:
        lenient().when(mockEncoder.encode("password")).thenReturn("myPassword");
        //when:
        userService.save("user", "password");
        //then:
        verify(mockMapper).save("user", "myPassword");
    }

    @Test
    public void testGetUserByUsername() {
        userService.getUserByUsername("username");
        verify(mockMapper).getUserByName("username");
    }

    @Test
    public void throwExceptionWhenUserNotFound() {
//        Mockito.when(mockMapper.getUserByName("username")).thenReturn(null);
        Assertions.assertThrows(UsernameNotFoundException.class,
                () -> userService.loadUserByUsername("username"));
    }

    @Test
    public void returnUserDetailsWhenUserFound() {
        when(mockMapper.getUserByName("username")).
                thenReturn(new User("username", "encodedPassword"));
        final UserDetails userDetails = userService.loadUserByUsername("username");
        Assertions.assertEquals("encodedPassword", userDetails.getPassword());
    }
}
