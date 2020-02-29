package com.climatetree.user.controller;

import com.climatetree.user.dto.Execution;
import com.climatetree.user.enums.Constants;
import com.climatetree.user.model.User;
import com.climatetree.user.service.UserService;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

  /**
   * Find all users map.
   *
   * @return the map
   */
  @GetMapping("")
  public Map<String, Object> getUsers() {
    Map<String, Object> resultMap = new HashMap<>();
    try {
      Execution<User> res = userService.findAllUsers();
      resultMap.put(Constants.SUCCESS.getStatusCode(), res.getResult());
      resultMap.put(Constants.USER.getStatusCode(), res.getObjects());

    } catch (Exception e) {
      resultMap.put(Constants.SUCCESS.getStatusCode(), false);
      resultMap.put(Constants.EXCEPTION.getStatusCode(), e.getMessage());
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
  public Map<String, Object> getUsersByName(String name) {
    Map<String, Object> resultMap = new HashMap<>();
    try {
      Execution<User> res = userService.getUsersByName(name);
      resultMap.put(Constants.SUCCESS.getStatusCode(), res.getResult());
      resultMap.put(Constants.USER.getStatusCode(), res.getObjects());
    } catch (Exception e) {
      resultMap.put(Constants.SUCCESS.getStatusCode(), false);
      resultMap.put(Constants.EXCEPTION.getStatusCode(), e.getMessage());
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
  public Map<String, Object> getUsersByRoleId(Integer roleid) {
    Map<String, Object> resultMap = new HashMap<>();
    try {
      Execution<User> res = userService.getUsersByRoleId(roleid);
      resultMap.put(Constants.SUCCESS.getStatusCode(), res.getResult());
      resultMap.put(Constants.USER.getStatusCode(), res.getObjects());
    } catch (Exception e) {
      resultMap.put(Constants.SUCCESS.getStatusCode(), false);
      resultMap.put(Constants.EXCEPTION.getStatusCode(), e.getMessage());
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
  public Map<String, Object> createUser(@RequestBody User newUser) {
    Map<String, Object> resultMap = new HashMap<>();
    try {
      Execution<User> res = userService.insertUser(newUser);
      resultMap.put(Constants.SUCCESS.getStatusCode(), res.getResult());
      resultMap.put(Constants.USER.getStatusCode(), res.getObjects());
    } catch (Exception e) {
      resultMap.put(Constants.SUCCESS.getStatusCode(), false);
      resultMap.put(Constants.EXCEPTION.getStatusCode(), e.getMessage());
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
  public Map<String, Object> deleteUser(@PathVariable Long userId) {
    Map<String, Object> resultMap = new HashMap<>();
    try {
      Execution<User> res = userService.deleteUser(userId);
      resultMap.put(Constants.SUCCESS.getStatusCode(), res.getResult());
      resultMap.put(Constants.USER.getStatusCode(), res.getObjects());
    } catch (Exception e) {
      resultMap.put(Constants.SUCCESS.getStatusCode(), false);
      resultMap.put(Constants.EXCEPTION.getStatusCode(), e.getMessage());
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
  public Map<String, Object> updateUserRole(HttpServletRequest request) {
    return null;
  }
}
