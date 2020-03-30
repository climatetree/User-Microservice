package com.climatetree.user.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.climatetree.user.dto.Execution;
import com.climatetree.user.enums.Constants;
import com.climatetree.user.enums.ResultEnum;
import com.climatetree.user.exception.InternalException;
import com.climatetree.user.model.Role;
import com.climatetree.user.model.User;
import com.climatetree.user.service.UserService;


@RunWith(SpringJUnit4ClassRunner.class)
public class UserControllerTest {

  private MockMvc mockMvc;



  // does not cover anything but to keep we may have to use something similar later
  @Test
  public void testCreateUser() {
    UserController controller = mock(UserController.class);

    User user = new User();
    user.setEmail("abc");
    user.setUserId(43L);
    user.setNickname("tom");

    Execution<User> exe = new Execution<>();

    exe.setObject(user);
    exe.setResult(ResultEnum.SUCCESS);
    try {
      Map<String, Object> res = controller.createUser(user);
    } catch (InternalException e) {
      e.printStackTrace();
    }

    Map<String, Object> result = new HashMap<>();
    result.put("userId", Constants.SUCCESS.getStatusCode());
    result.put("username", Constants.SUCCESS.getStatusCode());
    result.put("email", Constants.SUCCESS.getStatusCode());
    result.put("role", Constants.SUCCESS.getStatusCode());

//    doReturn(result).when(controller).createUser(user);
    //when(controller.createUser(user)).thenReturn(result);

    try {
      verify(controller).createUser(user);
    } catch (InternalException e) {
      e.printStackTrace();
    }

    assertEquals("success",  result.get("userId"));
    assertEquals("success",  result.get("email"));
    assertEquals("success",  result.get("username"));
    assertEquals("success",  result.get("role"));


    assertEquals(User.class, exe.getObject().getClass());
  }


  @Test
  public void testFindUserByName() {

    // setup
    UserService mockService = mock(UserService.class);
    UserController controller = new UserController(mockService);
    User user = new User();
    user.setEmail("abc");
    user.setNickname("john");
    user.setUserId(33L);

    Execution<User> exe = new Execution<>();
    exe.setObject(user);

    exe.setResult(ResultEnum.SUCCESS);
    when(mockService.insertUser(user)).thenReturn(exe);
    Map<String, Object> res = null;
    try {
      res = controller.createUser(user);
    } catch (InternalException e) {
      e.printStackTrace();
    }

    // test
    verify(mockService).insertUser(user);
    assertEquals(ResultEnum.SUCCESS, res.get(Constants.SUCCESS.getStatusCode()));
    assertEquals(User.class, exe.getObject().getClass());

    when(mockService.getUsersByName("john")).thenReturn(exe);
    Map<String, Object> resTwo = null;
    try {
      resTwo = controller.getUsersByName("john");
    } catch (InternalException e) {
      e.printStackTrace();
    }
    verify(mockService).getUsersByName("john");
    assertEquals(ResultEnum.SUCCESS,  resTwo.get(Constants.SUCCESS.getStatusCode()));

    assertEquals(User.class, exe.getObject().getClass());
  }

  @Test
  public void testFindUserById() {

    // setup
    UserService mockService = mock(UserService.class);
    UserController controller = new UserController(mockService);
    User user = new User();
    user.setEmail("abc");
    user.setNickname("john");
    user.setEmail("john@gmail.com");
    user.setUserId(33L);
    user.setRole(new Role(1, "ADMIN"));

    Execution<User> exe = new Execution<>();
    exe.setObject(user);

    exe.setResult(ResultEnum.SUCCESS);
    when(mockService.insertUser(user)).thenReturn(exe);
    Map<String, Object> res = null;
    try {
      res = controller.createUser(user);
    } catch (InternalException e) {
      e.printStackTrace();
    }

    // test
    verify(mockService).insertUser(user);
    assertEquals(ResultEnum.SUCCESS, res.get(Constants.SUCCESS.getStatusCode()));
    assertEquals(User.class, exe.getObject().getClass());


    when(mockService.getUsersByRoleId(1)).thenReturn(exe);
    Map<String, Object> resTwo = null;
    try {
      resTwo = controller.getUsersByRoleId(1);
    } catch (InternalException e) {
      e.printStackTrace();
    }
    verify(mockService).getUsersByRoleId(1);
    assertEquals(ResultEnum.SUCCESS,  resTwo.get(Constants.SUCCESS.getStatusCode()));

    assertEquals(User.class, exe.getObject().getClass());
  }



  @Test
  public void findUserByEmail() throws InternalException {
    // setup
    UserService mockService = mock(UserService.class);
    UserController controller = new UserController(mockService);
    User user = new User();
    user.setEmail("abc");
    user.setNickname("john");
    user.setEmail("john@gmail.com");
    user.setUserId(33L);
    user.setRole(new Role(1, "ADMIN"));

    Execution<User> exe = new Execution<>();
    exe.setObject(user);

    exe.setResult(ResultEnum.SUCCESS);
    when(mockService.insertUser(user)).thenReturn(exe);
    Map<String, Object> res = controller.createUser(user);

    // test
    verify(mockService).insertUser(user);
    assertEquals(ResultEnum.SUCCESS, res.get(Constants.SUCCESS.getStatusCode()));
    assertEquals(User.class, exe.getObject().getClass());

    when(mockService.getUser(33L)).thenReturn(exe);
    Map<String, Object> resTwo = controller.getUsers();
    verify(mockService).findAllUsers();
    assertEquals(ResultEnum.SUCCESS,  resTwo.get(Constants.SUCCESS.getStatusCode()));

    assertEquals(User.class, exe.getObject().getClass());
  }
}

