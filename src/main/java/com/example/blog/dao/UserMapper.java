package com.example.blog.dao;

import com.example.blog.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Select("select * from user where username=#{username}")
    User getUserByName(@Param("username") String username);

    @Insert("insert into user (username,encrypted_password,avatar,create_at) values (#{username},#{encryptedPassword},#{avatar},now())")
    void save(@Param("username") String username, @Param("encryptedPassword") String password, @Param("avatar") String avatar);

    @Select("select * from user where id=#{id}")
    User getUserById(@Param("id") int userId);
}
