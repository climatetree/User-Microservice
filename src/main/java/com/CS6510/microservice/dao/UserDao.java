package com.CS6510.microservice.dao;

import com.CS6510.microservice.model.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao extends JpaRepository<User, Long> {
    /**
     * get all users in the user table.
     * @return A List of User objects.
     */
    List<User> findAll();

    /**
     * Get a single User object by userId.
     * @param userId A Long object userId.
     * @return A User object.
     */
    User findByUserId(Long userId);

    /**
     * Save a new User Entry into database. Could be served as both iinsert and update.
     * @param user A User object.
     * @return A User object.
     */
    User save(User user);

    /**
     * Delete a user entry in the database by given userId.
     * @param userId A Long object userId.
     */
    void deleteById(Long userId);


    /**
     * Get all users with certain nickname.
     * @param name A String of users' nickname.
     * @return A list of user Objects
     */
    List<User> findByNickname(String name);

    /**
     * Get all users with certain roleId.
     * @param roleId A integer of users' roleId.
     * @return A list of user Objects
     */
    List<User> findByRoleId(Integer roleId);
}
