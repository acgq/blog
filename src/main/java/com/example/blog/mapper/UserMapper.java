package com.example.blog.mapper;

import com.example.blog.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Select("select * from user where id=#{id}")
    User getUserById(@Param("id") int id);

    @Select("select * from user where name=#{username}")
    User getUserByName(@Param("username") String username);

    @Insert("insert into user (name,encrypted_password,create_at) values (#{name},#{encryptedPassword},now())")
    void save(@Param("name") String username, @Param("encryptedPassword") String password);
}
