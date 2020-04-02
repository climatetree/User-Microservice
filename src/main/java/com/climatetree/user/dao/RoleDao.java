package com.climatetree.user.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.climatetree.user.model.Role;

/**
 * The interface Role dao.
 */
@Repository
public interface RoleDao extends JpaRepository<Role, Integer> {
    /**
     * get all users in the user table.
     * @return A List of User objects.
     */
    List<Role> findAll();

    /**
     * Get a single Role object by roleId.
     *
     * @param roleId An Integer object roleId.
     * @return A Role object.
     */
    Role findByRoleId(Integer roleId);

    Role findByName(String name);

}