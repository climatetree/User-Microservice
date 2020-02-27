package com.CS6510.microservice.controller;

import com.CS6510.microservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    // test function
    @GetMapping("/test")
    public Map<String, Object> findAllUsers() {
        System.out.println("test");
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("success", true);
        return resultMap;
    }

    @GetMapping("/name")
    public Map<String, Object> findUsersByName(@RequestParam(value = "name") String name) {
        System.out.println("findUsersByName");
        return null;
    }

    @GetMapping("/test1")
    public String findUsersByName() {
        return "hello world";
    }

    @DeleteMapping("/")
    public Map<String, Object> deleteUser(@RequestParam(value = "userId") Long userId) {
        return null;
    }

    @PostMapping("/")
    public Map<String, Object> createUser(HttpServletRequest request) {
        return null;
    }

    @PutMapping("/")
    public Map<String, Object> updateUserRole(HttpServletRequest request) {
        return null;
    }
}
