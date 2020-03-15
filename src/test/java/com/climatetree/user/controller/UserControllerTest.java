package com.climatetree.user.controller;

import com.climatetree.user.dao.UserDao;
import com.climatetree.user.dto.Execution;
import com.climatetree.user.enums.Constants;
import com.climatetree.user.enums.ResultEnum;
import com.climatetree.user.model.User;
import com.climatetree.user.service.UserService;
import org.apache.tomcat.util.http.parser.MediaType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import sun.jvm.hotspot.HelloWorld;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringJUnit4ClassRunner.class)
public class UserControllerTest {

  private MockMvc mockMvc;

  @InjectMocks
  private HelloWorldController userController;


  @Before
  public void setUp() throws Exception{
    mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

  }

  @Test
  public void testOne() throws Exception{
    mockMvc.perform(MockMvcRequestBuilders.get("/hello")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(content().string("Hello World"));
  }

  // does not cover anything but to keep we may have to use something similar later
  @Test
  public void testCreateUser() {
    UserController controller = mock(UserController.class);
    User user = new User();
    user.setEmail("abc");
    user.setUserId(43L);

    Execution<User> exe = new Execution<>();
    exe.setObject(user);
    exe.setResult(ResultEnum.SUCCESS);

    Map<String, Object> result = controller.createUser(user);

    doReturn(result).when(controller).createUser(user);

    verify(controller).createUser(user);

    assertEquals("success",  result.get("status"));

    assertEquals(User.class, exe.getObject().getClass());
  }

  // does not cover anything but to keep we may have to use something similar later
  @Test
  public void testCreateUserTwo() {
    UserController controller = mock(UserController.class);
    User user = new User();
    user.setEmail("abc");
    user.setUserId(43L);
    user.setNickname("tom");

    Execution<User> exe = new Execution<>();

    exe.setObject(user);
    exe.setResult(ResultEnum.SUCCESS);
    Map<String, Object> res = controller.createUser(user);

    Map<String, Object> result = new HashMap<>();
    result.put("userId", Constants.SUCCESS.getStatusCode());
    result.put("username", Constants.SUCCESS.getStatusCode());
    result.put("email", Constants.SUCCESS.getStatusCode());
    result.put("role", Constants.SUCCESS.getStatusCode());

    doReturn(result).when(controller).createUser(user);
    //when(controller.createUser(user)).thenReturn(result);

    verify(controller).createUser(user);

    assertEquals("success",  result.get("userId"));
    assertEquals("success",  result.get("email"));
    assertEquals("success",  result.get("username"));
    assertEquals("success",  result.get("role"));

    assertEquals(User.class, exe.getObject().getClass());
  }


  @Test
  public void findUserById() {
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
    Map<String, Object> res = controller.createUser(user);

    // test
    verify(mockService).insertUser(user);
    assertEquals(ResultEnum.SUCCESS,  res.get(Constants.SUCCESS.getStatusCode()));
    assertEquals(User.class, exe.getObject().getClass());

    when(mockService.getUsersByName("john")).thenReturn(exe);
    Map<String, Object> resTwo = controller.getUsersByName("john");
    verify(mockService).getUsersByName("john");
    assertEquals(ResultEnum.SUCCESS,  resTwo.get(Constants.SUCCESS.getStatusCode()));
    assertEquals(User.class, exe.getObject().getClass());
  }

  @Test
  public void findUserByEmail() {
    // setup
    UserService mockService = mock(UserService.class);
    UserController controller = new UserController(mockService);
    User user = new User();
    user.setEmail("abc");
    user.setNickname("john");
    user.setEmail("john@gmail.com");
    user.setUserId(33L);
    user.setRoleId(1);

    Execution<User> exe = new Execution<>();
    exe.setObject(user);

    exe.setResult(ResultEnum.SUCCESS);
    when(mockService.insertUser(user)).thenReturn(exe);
    Map<String, Object> res = controller.createUser(user);

    // test
    verify(mockService).insertUser(user);
    assertEquals(ResultEnum.SUCCESS,  res.get(Constants.SUCCESS.getStatusCode()));
    assertEquals(User.class, exe.getObject().getClass());

    when(mockService.getUsersByRoleId(1)).thenReturn(exe);
    Map<String, Object> resTwo = controller.getUsersByRoleId(1);
    verify(mockService).getUsersByRoleId(1);
    assertEquals(ResultEnum.SUCCESS,  resTwo.get(Constants.SUCCESS.getStatusCode()));
    assertEquals(User.class, exe.getObject().getClass());
  }
}

