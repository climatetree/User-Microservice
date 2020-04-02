package com.climatetree.user.dao;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.climatetree.user.model.Role;
import com.climatetree.user.model.User;


//@SpringBootTest
//@RunWith(SpringRunner.class)
public class UserDaoTest {

  private UserDao mockDao;
  private RoleDao mockRoleDao;


  @Test
  void findByUserId() throws Exception {
    Date accountCreationDate = new Date(2018 / 02 / 20);
    Date lastLoginDate = new Date(2019 / 12 / 24);
    mockDao = mock(UserDao.class);
    mockRoleDao = mock(RoleDao.class);
    
    when(mockRoleDao.findByRoleId(1)).thenReturn(new Role(1, "ADMIN"));
    Long userId = 20000L;
    User user = new User();
    user.setEmail("tom@gmail.com");
    user.setBlacklisted(false);
    user.setLastLoginLocation("London");
    user.setLastLoginTime(lastLoginDate);
    user.setRegistrationDate(accountCreationDate);
    user.setNickname("john");
    user.setRole(mockRoleDao.findByRoleId(1));
    user.setUserId(userId);
    System.out.println(user.getNickname());
    when(mockDao.save(user)).thenReturn(user);
    mockDao.save(user);
    when(mockDao.findByUserId(20000L)).thenReturn(user);
    User testUser = mockDao.findByUserId(20000L);
    Assertions.assertEquals(20000L, testUser.getUserId());
    Assertions.assertEquals("tom@gmail.com", testUser.getEmail());
    Assertions.assertEquals(false, testUser.getBlacklisted());
    Assertions.assertEquals("London", testUser.getLastLoginLocation());
    Assertions.assertEquals("john", testUser.getNickname());
    Assertions.assertEquals(1, testUser.getRole().getRoleId());
    Assertions.assertEquals(accountCreationDate, testUser.getRegistrationDate());
    Assertions.assertEquals(lastLoginDate, testUser.getLastLoginTime());
  }


  @Test
  void testFindByName() throws Exception {
    mockDao = mock(UserDao.class);
    User userOne = new User();
    userOne.setNickname("tom");

    User userTwo = new User();
    userTwo.setNickname("john");

    List<User> list = new ArrayList<>();
    list.add(userOne);
    list.add(userTwo);

    when(mockDao.findAll()).thenReturn(list);
    List<User> ret = mockDao.findAll();

    assertEquals(User.class, ret.get(0).getClass());
    assertEquals(ArrayList.class, ret.getClass());
    assertEquals(list.size(), ret.size());
  }


}