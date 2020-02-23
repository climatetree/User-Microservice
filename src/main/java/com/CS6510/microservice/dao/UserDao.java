package com.CS6510.microservice.dao;

import com.CS6510.microservice.model.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDao implements IUserDao{
    @Override
    public User getUser(Long userId) {
        return null;
    }

    @Override
    public List<User> getUsers() {
        return null;
    }

    @Override
    public Integer insertUser(User user) {
        return null;
    }

    @Override
    public Integer updateUser() {
        return null;
    }

    @Override
    public Integer deleteUser(Long userId) {
        return null;
    }
}
