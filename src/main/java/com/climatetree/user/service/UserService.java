package com.climatetree.user.service;

import com.climatetree.user.dao.UserDao;
import com.climatetree.user.dto.Execution;
import com.climatetree.user.enums.ResultEnum;
import com.climatetree.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The type User service.
 */
@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    /**
     * Gets user.
     *
     * @param userId the user id
     * @return the user
     */
    public Execution<User> getUser(Long userId) {
        Execution<User> res;
        try {
            User user = userDao.findByUserId(userId);
            res = new Execution<>(ResultEnum.SUCCESS, user);
        } catch (Exception e) {
            res = new Execution<>(ResultEnum.DATABASE_ERROR);
        }
        return res;
    }

    /**
     * Gets users.
     *
     * @return the users
     */
    public Execution<User> findAllUsers() {
        Execution<User> res;
        try {
            List<User> users = userDao.findAll();
            res = new Execution<>(ResultEnum.SUCCESS, users);
        } catch (Exception e) {
            res = new Execution<>(ResultEnum.DATABASE_ERROR);
        }
        return res;
    }

    /**
     * Insert user execution.
     *
     * @param user the user
     * @return the execution
     */
    public Execution<User> insertUser(User user) {
        Execution<User> res;
        try {
            userDao.save(user);
            res = new Execution<>(ResultEnum.SUCCESS, 1);
        } catch (Exception e) {
            res = new Execution<>(ResultEnum.DATABASE_ERROR);
        }
        return res;
    }


    /**
     * Delete user execution.
     *
     * @param userId the user id
     * @return the execution
     */
    public Execution<User> deleteUser(Long userId) {
        Execution<User> res;
        try {
            userDao.deleteById(userId);
            res = new Execution<>(ResultEnum.SUCCESS, 1);
        } catch (Exception e) {
            res = new Execution<>(ResultEnum.DATABASE_ERROR);
        }
        return res;
    }

    /**
     * Gets users by name.
     *
     * @param name the name
     * @return the users by name
     */
    public Execution<User> getUsersByName(String name) {
        Execution<User> res;
        try {
            List<User> users = userDao.findByNickname(name);
            res = new Execution<>(ResultEnum.SUCCESS, users);
        } catch (Exception e) {
            res = new Execution<>(ResultEnum.DATABASE_ERROR);
        }
        return res;
    }

    /**
     * Gets users by role id.
     *
     * @param roleId the role id
     * @return the users by role id
     */
    public Execution<User> getUsersByRoleId(Integer roleId) {
        Execution<User> res ;
        try {
            List<User> users = userDao.findByRoleId(roleId);
            res = new Execution<>(ResultEnum.SUCCESS, users);
        } catch (Exception e) {
            res = new Execution<>(ResultEnum.DATABASE_ERROR);
        }
        return res;
    }
}
