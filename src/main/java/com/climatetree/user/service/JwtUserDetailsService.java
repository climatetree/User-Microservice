package com.climatetree.user.service;

import com.climatetree.user.dao.UserDao;
import com.climatetree.user.enums.Constants;
import com.climatetree.user.model.User;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService {

  @Autowired
  UserDao userDao;

  public User loadUserByUsername(String username, String email) {
    User user = userDao.findByEmail(email);
    if (user == null) {
      user = this.saveUserDetails(username,email);
      userDao.save(user);
    }
    return new User(user);
  }

  public User saveUserDetails(String username,String email){
    User user = new User();
    user.setNickname(username);
    user.setEmail(email);
    //just setting advanced user for now
    user.setRoleId(Integer.parseInt(Constants.ADV_USERS.getStatusCode()));
    user.setLastLoginLocation(Constants.LAST_LOGIN.getStatusCode());
    user.setLastLoginTime(new Date());
    user.setRegistrationDate(new Date());
    return user;
  }
}