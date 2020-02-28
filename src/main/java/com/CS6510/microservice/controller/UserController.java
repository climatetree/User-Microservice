package com.CS6510.microservice.controller;

import com.CS6510.microservice.dto.Execution;
import com.CS6510.microservice.model.User;
import com.CS6510.microservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The UserController translates interaction with the front-end user interface into actions to be performed by our "User" model.
 */
@RestController
@RequestMapping("/user")
public class UserController {


    // The userService component is the bridge between the controller and the DAO
    @Autowired
    private UserService userService;


    // test function
    @GetMapping("")
    public Map<String, Object> findAllUsers() {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            Execution<User> res = userService.getUsers();
            resultMap.put("success", res.getResult());
            resultMap.put("users", res.getObjects());

        } catch (Exception e) {
            resultMap.put("success", false);
            resultMap.put("exception", e.getMessage());
        }
        return resultMap;
    }

    /**
     * Search a user by name
     * @param name
     * @return
     */
    @GetMapping("/searchname")
    public Map<String, Object> findUsersByName(String name) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            Execution<User> res = userService.getUsersByName(name);
            resultMap.put("success", res.getResult());
            resultMap.put("users", res.getObjects());

        } catch (Exception e) {
            resultMap.put("success", false);
            resultMap.put("exception", e.getMessage());
        }
        return resultMap;
    }

    /**
     * Get all users based on their roles
     * @param roleid
     * @return
     */
    @GetMapping("/searchrole")
    public Map<String, Object> findUsersByRoleId(Integer roleid) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            Execution<User> res = userService.getUsersByRoleId(roleid);
            resultMap.put("success", res.getResult());
            resultMap.put("users", res.getObjects());

        } catch (Exception e) {
            resultMap.put("success", false);
            resultMap.put("exception", e.getMessage());
        }
        return resultMap;
    }

    /**
     * add a new user
     * @param newUser
     * @return
     */
    @PostMapping("/addUser")
    public Map<String, Object> createUser(@RequestBody User newUser) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            Execution<User> res = userService.insertUser(newUser);
            resultMap.put("success", res.getResult());
            resultMap.put("users", res.getObjects());

        } catch (Exception e) {
            resultMap.put("success", false);
            resultMap.put("exception", e.getMessage());
        }
        return resultMap;
    }

    /**
     * delete a user
     * @param userId
     * @return
     */
    @DeleteMapping(path = "/{userId}")
    public Map<String, Object> deleteUser(@PathVariable Long userId) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            Execution<User> res = userService.deleteUser(userId);
            resultMap.put("success", res.getResult());
            resultMap.put("users", res.getObjects());

        } catch (Exception e) {
            resultMap.put("success", false);
            resultMap.put("exception", e.getMessage());
        }
        return resultMap;
    }

    @PutMapping("/")
    public Map<String, Object> updateUserRole(HttpServletRequest request) {
        return null;
    }
}
