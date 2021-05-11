package com.gdpu.homework.ServiceImpl;

import com.gdpu.homework.Config.DisableToken;
import com.gdpu.homework.Entity.User;
import com.gdpu.homework.Mapper.UserMapper;
import com.gdpu.homework.ServiceImpl.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    @Override
    public User checkUser(User user) {
       return userMapper.getUserWithPassword(user);


    }
    @Override
    public List<User> getUsers(){
        return userMapper.getUsers();
    }
    @Override
    public  List<User> getUsersByUsername (String username){
        return  userMapper.getUsersByUsername(username);
    }
    @Override
    public void updatePassword(String username,String password){
        userMapper.updatePassword(username, password);
    }
    @Override
    public void deleteUser(String username){
        userMapper.deleteUser(username);
    }
    @Override
    public void updateRoot(String username,int root){
        userMapper.updateRoot(username,root);
    }
    @Override
    public void insertUser(User user){
        userMapper.insertUser(user);
    }
    @Override
    public String  selectOneUser(User user){
        return  userMapper.selectOneUser(user);
    }
}
