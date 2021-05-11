package com.gdpu.homework.ServiceImpl.Service;

import com.gdpu.homework.Entity.User;


import java.util.List;


public interface UserService {
    public User checkUser(User user);
    public List<User> getUsers();
    public  List<User> getUsersByUsername (String username);
    public void updatePassword(String username,String password);
    public void deleteUser(String username);
    public void updateRoot(String username,int root);
    public void insertUser(User user);
    public String  selectOneUser(User user);
}
