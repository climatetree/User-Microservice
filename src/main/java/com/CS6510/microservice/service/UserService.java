package com.CS6510.microservice.service;

import com.CS6510.microservice.dao.UserDao;
import com.CS6510.microservice.dto.Execution;
import com.CS6510.microservice.enums.ResultEnum;
import com.CS6510.microservice.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    public Execution<User> getUser(Long userId) throws Exception {
        Execution<User> res = null;
        try {
            User user = userDao.findByUserId(userId);
            res = new Execution<>(ResultEnum.SUCCESS, user);
        } catch (Exception e) {
            res = new Execution<>(ResultEnum.DATABASE_ERROR);
        }
        return res;
    }

    public Execution<User> getUsers() throws Exception {
        Execution<User> res = null;
        try {
            List<User> users = userDao.findAll();
            res = new Execution<>(ResultEnum.SUCCESS, users);
        } catch (Exception e) {
            res = new Execution<>(ResultEnum.DATABASE_ERROR);
        }
        return res;
    }

    public Execution<User> insertUser(User user) throws Exception {
        Execution<User> res = null;
        try {
            userDao.save(user);
            res = new Execution<>(ResultEnum.SUCCESS, 1);
        } catch (Exception e) {
            res = new Execution<>(ResultEnum.DATABASE_ERROR);
        }
        return res;
    }


    public Execution<User> deleteUser(Long userId) throws Exception {
        Execution<User> res = null;
        try {
            userDao.deleteById(userId);
            res = new Execution<>(ResultEnum.SUCCESS, 1);
        } catch (Exception e) {
            res = new Execution<>(ResultEnum.DATABASE_ERROR);
        }
        return res;
    }

    public Execution<User> getUsersByName(String name) throws Exception {
        Execution<User> res = null;
        try {
            List<User> users = userDao.findByNickname(name);
            res = new Execution<>(ResultEnum.SUCCESS, users);
        } catch (Exception e) {
            res = new Execution<>(ResultEnum.DATABASE_ERROR);
        }
        return res;
    }

    public Execution<User> getUsersByRoleId(Integer roleId) throws Exception {
        Execution<User> res = null;
        try {
            List<User> users = userDao.findByRoleId(roleId);
            res = new Execution<>(ResultEnum.SUCCESS, users);
        } catch (Exception e) {
            res = new Execution<>(ResultEnum.DATABASE_ERROR);
        }
        return res;
    }
}
