package com.climatetree.user.service;

import com.climatetree.user.dao.UserDao;
import com.climatetree.user.dto.Execution;
import com.climatetree.user.model.User;
import com.climatetree.user.enums.ResultEnum;
import java.util.Date;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


class UserServiceTest {
    @Autowired
    private UserDao userDao;

    @Autowired
    private UserService userService;

    @Test
    void getUserEmail() throws Exception {
        UserDao mockDao = Mockito.mock(UserDao.class);
        UserService service = new UserService(mockDao);
        User newUser = new User();
        newUser.setEmail("tom@gmail.com");
        when(mockDao.findByUserId(1l)).thenReturn(newUser);

        Execution<User> exe = service.getUser(1l);
        User resUser = exe.getObject();

        assertEquals(User.class, resUser.getClass());
        assertEquals(ResultEnum.SUCCESS, exe.getResult());
        assertEquals("tom@gmail.com", resUser.getEmail());
    }

    @Test
    void getUserId() throws Exception {
        UserDao mockDao = Mockito.mock(UserDao.class);
        UserService service = new UserService(mockDao);
        User newUser = new User();
        newUser.setUserId(101L);
        when(mockDao.findByUserId(1l)).thenReturn(newUser);
        Execution<User> exe = service.getUser(1l);
        User resUser = exe.getObject();
        assertEquals(User.class, resUser.getClass());
        assertEquals(ResultEnum.SUCCESS, exe.getResult());
        assertEquals(101, resUser.getUserId().intValue());
    }


    @Test
    void getUserName() throws Exception {
        UserDao mockDao = Mockito.mock(UserDao.class);
        UserService service = new UserService(mockDao);
        User newUser = new User();
        newUser.setNickname("John");
        when(mockDao.findByUserId(1l)).thenReturn(newUser);

        Execution<User> exe = service.getUser(1l);
        User resUser = exe.getObject();
        assertEquals(User.class, resUser.getClass());
        assertEquals(ResultEnum.SUCCESS, exe.getResult());
        assertEquals("John", resUser.getNickname());
    }

    @Test
    void getUserRole() throws Exception {
        UserDao mockDao = Mockito.mock(UserDao.class);
        UserService service = new UserService(mockDao);
        User newUser = new User();
        newUser.setRoleId(2);
        when(mockDao.findByUserId(1l)).thenReturn(newUser);

        Execution<User> exe = service.getUser(1l);
        User resUser = exe.getObject();
        assertEquals(User.class, resUser.getClass());
        assertEquals(ResultEnum.SUCCESS, exe.getResult());
        assertEquals(2, resUser.getRoleId().intValue());
    }


    @Test
    void isUserFlagged() throws Exception {
        UserDao mockDao = Mockito.mock(UserDao.class);
        UserService service = new UserService(mockDao);
        User newUser = new User();
        newUser.setFlag(false);
        when(mockDao.findByUserId(1l)).thenReturn(newUser);

        Execution<User> exe = service.getUser(1l);
        User resUser = exe.getObject();
        assertEquals(User.class, resUser.getClass());
        assertEquals(ResultEnum.SUCCESS, exe.getResult());
        assertEquals(false, resUser.getFlag());
    }

    @Test
    void getRegistrationDate() throws Exception {
        UserDao mockDao = Mockito.mock(UserDao.class);
        UserService service = new UserService(mockDao);
        User newUser = new User();
        newUser.setRegistrationDate(new Date(2019/10/23));
        when(mockDao.findByUserId(1l)).thenReturn(newUser);

        Execution<User> exe = service.getUser(1l);
        User resUser = exe.getObject();
        assertEquals(User.class, resUser.getClass());
        assertEquals(ResultEnum.SUCCESS, exe.getResult());
        assertEquals(new Date(2019/10/23), resUser.getRegistrationDate());
    }

    @Test
    void getLastLoginTime() throws Exception {
        UserDao mockDao = Mockito.mock(UserDao.class);
        UserService service = new UserService(mockDao);
        User newUser = new User();
        newUser.setLastLoginTime(new Date(2020/01/18));
        when(mockDao.findByUserId(1l)).thenReturn(newUser);

        Execution<User> exe = service.getUser(1l);
        User resUser = exe.getObject();
        assertEquals(User.class, resUser.getClass());
        assertEquals(ResultEnum.SUCCESS, exe.getResult());
        assertEquals(new Date(2020/01/18), resUser.getLastLoginTime());
    }

    @Test
    public void getUserByEmailTest() {
        UserDao mockDao = Mockito.mock(UserDao.class);
        UserService service = new UserService(mockDao);
        User newUser = new User();
        newUser.setUserId(1l);
        when(mockDao.findByUserId(1l)).thenReturn(newUser);

        Execution<User> exe = service.getUser(1l);
        User resUser = exe.getObject();

        assertEquals(User.class, resUser.getClass());
        assertEquals(ResultEnum.SUCCESS, exe.getResult());
        assertEquals(1l, (long)resUser.getUserId());
        verify(mockDao).findByUserId((long)1);
    }
}