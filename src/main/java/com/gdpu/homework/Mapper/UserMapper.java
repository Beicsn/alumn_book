package com.gdpu.homework.Mapper;

import com.gdpu.homework.Config.DisableToken;
import com.gdpu.homework.Entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

@Repository
public interface UserMapper {
    @Select("select * from user where username= #{user.username} and password = #{user.password}")
    public User getUserWithPassword(@Param("user") User user);
    @Select("select username as username,root as root from user")
    public List<User> getUsers();
    @Select("select username as username,root as root from user where  username  like '%${username}%'")
    public  List<User> getUsersByUsername (@Param("username") String username);
    @Update("update user set password = #{password} where username = #{username}")
    public  void updatePassword(@Param("username")String username, @Param("password") String password);
    @Delete("delete from user where username = #{username}")
    public void deleteUser(@Param("username") String username);
    @Update("update user set root = #{root} where username = #{username}")
    public void updateRoot(@Param("username") String username,@Param("root") int root);
    @Insert("insert into user(username,password,root) values(#{username},#{password},#{root})")
    public void insertUser(User user);
    @Select("select username from user where username = #{username}")
    public String  selectOneUser(User user);
}
