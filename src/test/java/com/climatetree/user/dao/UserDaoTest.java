package com.climatetree.user.dao;

import com.climatetree.user.model.User;

import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


//@SpringBootTest
//@RunWith(SpringRunner.class)
class UserDaoTest {

    private UserDao mockDao;


    @Test
    void findByUserId() throws Exception{
        Date accountCreationDate = new Date(2018/02/20);
        Date lastLoginDate = new Date(2019/12/24);
        mockDao = mock(UserDao.class);
        Long userId = 20000L;
        User user = new User();
        user.setEmail("tom@gmail.com");
        user.setFlag(true);
        user.setLastLoginLocation("London");
        user.setLastLoginTime(lastLoginDate);
        user.setRegistrationDate(accountCreationDate);
        user.setNickname("john");
        user.setRoleId(1);
        user.setUserId(userId);
        System.out.println(user.getNickname());
        when(mockDao.save(user)).thenReturn(user);
        mockDao.save(user);
        when(mockDao.findByUserId(20000L)).thenReturn(user);
        User testUser = mockDao.findByUserId(20000L);
        Assertions.assertEquals(20000L, testUser.getUserId());
        Assertions.assertEquals("tom@gmail.com", testUser.getEmail());
        Assertions.assertEquals(true, testUser.getFlag());
        Assertions.assertEquals("London", testUser.getLastLoginLocation());
        Assertions.assertEquals("john", testUser.getNickname());
        Assertions.assertEquals(1, testUser.getRoleId());
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