package org.CS6510.microservice.dao;

import org.CS6510.microservice.dto.User;

import java.util.List;

public interface UserDAO {
    // CRUD
    // The return value of insert, update, delete should be the num of rows effected.
    // we might use mybatis or hibernate to generate this dao.
    User getUser(Long userId);
    List<User> getUsers();
    Integer insertUser(User user);
    Integer updateUser();
    Integer deleteUser(Long userId);
}
