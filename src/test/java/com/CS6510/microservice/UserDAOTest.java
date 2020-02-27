package com.CS6510.microservice;

import com.CS6510.microservice.dao.UserDao;
import com.CS6510.microservice.model.User;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserDAOTest {

  @Autowired
  private UserDao userDao;
  @Test
  void findUser(){
    User guest = userDao.getUser(1L);
    System.out.println(guest);
  }
}
