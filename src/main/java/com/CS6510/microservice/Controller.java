package com.CS6510.microservice;

import com.CS6510.microservice.model.User;
import com.CS6510.microservice.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class Controller {

  @PostMapping("/hello")
  public String hello(){
    return "hello word";
  }

  @Autowired
  private UserRepository repository;

  @PostMapping("/login")
  public String saveUser(@RequestBody User user){
    if(repository.existsById(user.getEmail())){
      return "the user already exits";
    } else {
      repository.save(user);
      return "a new user was added his name is: " + user.getFirst_name() + " " + user.getLast_name() + " created on " + user.getRegistration_date();
    }
  }

  public List<User> getUsers(){
    return repository.findAll();
  }

}
