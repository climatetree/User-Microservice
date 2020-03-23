package com.climatetree.user.controller;

import com.climatetree.user.dto.Execution;
import com.climatetree.user.enums.Constants;
import com.climatetree.user.exception.InternalException;
import com.climatetree.user.model.User;
import com.climatetree.user.service.UserService;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * The UserController translates interaction with the front-end user interface into actions to be
 * performed by our "User" model.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    // The userService component is the bridge between the controller and the DAO
    @Autowired
    private UserService userService;

    public UserController() {
    }

    public UserController(UserService service) {
        this.userService = service;
    }

    /**
     * Find all users map.
     *
     * @return the map
     */
    @GetMapping("")
    @ResponseBody
    public Map<String, Object> getUsers() throws InternalException {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            Execution<User> res = userService.findAllUsers();
            resultMap.put(Constants.USER.getStatusCode(), res.getObjects());
        } catch (Exception e) {
            throw new InternalException(e.getMessage());
        }

        return resultMap;
    }

    /**
     * Find users by name map.
     *
     * @param name the name
     * @return the map
     */
    @GetMapping("/searchname")
    @ResponseBody
    public Map<String, Object> getUsersByName(String name) throws InternalException {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            Execution<User> res = userService.getUsersByName(name);
            resultMap.put(Constants.SUCCESS.getStatusCode(), res.getResult());
            resultMap.put(Constants.USER.getStatusCode(), res.getObjects());
        } catch (Exception e) {
            throw new InternalException(e.getMessage());
        }
        return resultMap;
    }

    /**
     * Get all users based on their roles
     *
     * @param roleid the roleid
     * @return map
     */
    @GetMapping("/searchrole")
    @ResponseBody
    public Map<String, Object> getUsersByRoleId(Integer roleid) throws InternalException {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            Execution<User> res = userService.getUsersByRoleId(roleid);
            resultMap.put(Constants.SUCCESS.getStatusCode(), res.getResult());
            resultMap.put(Constants.USER.getStatusCode(), res.getObjects());
        } catch (Exception e) {
            throw new InternalException(e.getMessage());

        }
        return resultMap;
    }

    /**
     * add a new user
     *
     * @param newUser the new user
     * @return map
     */
    @PostMapping("/addUser")
    @ResponseBody
    public Map<String, Object> createUser(@RequestBody User newUser) throws InternalException {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            Execution<User> res = userService.insertUser(newUser);
            resultMap.put(Constants.SUCCESS.getStatusCode(), res.getResult());
            resultMap.put(Constants.USER.getStatusCode(), res.getObjects());
        } catch (Exception e) {
            throw new InternalException(e.getMessage());

        }
        return resultMap;
    }

    /**
     * find all users that has been flagged (true) --> to moderate
     *
     * @return a list of user that has been been flagged
     */
    @GetMapping("/flagged_users")
    public Map<String, Object> getFlaggedUsers() throws InternalException {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            Execution<User> res = userService.getFlaggedUsers();
            resultMap.put(Constants.USER.getStatusCode(), res.getObjects());
        } catch (Exception e) {
            throw new InternalException(e.getMessage());
        }
        return resultMap;
    }

    /**
     * delete a user
     *
     * @param userId the user id
     * @return map
     */
    @DeleteMapping(path = "/{userId}")
    @ResponseBody
    public Map<String, Object> deleteUser(@PathVariable Long userId) throws InternalException {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            Execution<User> res = userService.deleteUser(userId);
            resultMap.put(Constants.SUCCESS.getStatusCode(), res.getResult());
            resultMap.put(Constants.USER.getStatusCode(), res.getObjects());
        } catch (Exception e) {
            throw new InternalException(e.getMessage());

        }
        return resultMap;
    }

    /**
     * Update user role map.
     *
     * @param request the request
     * @return the map
     */
    @PutMapping("/")
    @ResponseBody
    public Map<String, Object> updateUserRole(HttpServletRequest request) {
        return null;
    }

}

