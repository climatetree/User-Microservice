package org.CS6510.microservice.dao;

import org.CS6510.microservice.dto.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDAOImpl implements UserDAO {
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
