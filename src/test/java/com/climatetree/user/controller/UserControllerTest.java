package com.climatetree.user.controller;

import com.climatetree.user.dto.Execution;
import com.climatetree.user.enums.Constants;
import com.climatetree.user.enums.ResultEnum;
import com.climatetree.user.model.User;
import com.climatetree.user.service.UserService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


@RunWith(SpringJUnit4ClassRunner.class)
public class UserControllerTest {

  private MockMvc mockMvc;

  @InjectMocks
  private HelloWorldController userController;


  @Before
  public void setUp() throws Exception {
    mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

  }

  @Test
  public void testOne() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/hello")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(content().string("Hello World"));
  }

  @Test
  public void testCreateUser() {
    // setup
    UserService mockService = mock(UserService.class);
    UserController controller = new UserController(mockService);
    User user = new User();
    user.setEmail("abc");
    Execution<User> exe = new Execution<>();
    exe.setObject(user);
    exe.setResult(ResultEnum.SUCCESS);
    when(mockService.insertUser(user)).thenReturn(exe);
    Map<String, Object> res = controller.createUser(user);

    // test
    verify(mockService).insertUser(user);
    assertEquals(ResultEnum.SUCCESS, res.get(Constants.SUCCESS.getStatusCode()));
    assertEquals(User.class, exe.getObject().getClass());
  }

  @Test
  public void testDeleteUser() {
    // setup
    UserService mockService = mock(UserService.class);
    UserController controller = new UserController(mockService);
    User user = new User();
    user.setEmail("abc");
    user.setUserId(33L);

    Execution<User> exe = new Execution<>();
    exe.setObject(user);

    exe.setResult(ResultEnum.SUCCESS);
    when(mockService.insertUser(user)).thenReturn(exe);
    Map<String, Object> res = controller.createUser(user);

    // test
    verify(mockService).insertUser(user);
    assertEquals(ResultEnum.SUCCESS, res.get(Constants.SUCCESS.getStatusCode()));
    assertEquals(User.class, exe.getObject().getClass());

    when(mockService.deleteUser(33L)).thenReturn(exe);
    Map<String, Object> resTwo = controller.deleteUser(33L);
    verify(mockService).deleteUser(33L);
    assertEquals(ResultEnum.SUCCESS, resTwo.get(Constants.SUCCESS.getStatusCode()));
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
    assertEquals(ResultEnum.SUCCESS, res.get(Constants.SUCCESS.getStatusCode()));
    assertEquals(User.class, exe.getObject().getClass());

    when(mockService.getUsersByName("john")).thenReturn(exe);
    Map<String, Object> resTwo = controller.getUsersByName("john");
    verify(mockService).getUsersByName("john");
    assertEquals(ResultEnum.SUCCESS, resTwo.get(Constants.SUCCESS.getStatusCode()));
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
    assertEquals(ResultEnum.SUCCESS, res.get(Constants.SUCCESS.getStatusCode()));
    assertEquals(User.class, exe.getObject().getClass());

    when(mockService.getUsersByRoleId(1)).thenReturn(exe);
    Map<String, Object> resTwo = controller.getUsersByRoleId(1);
    verify(mockService).getUsersByRoleId(1);
    assertEquals(ResultEnum.SUCCESS, resTwo.get(Constants.SUCCESS.getStatusCode()));
    assertEquals(User.class, exe.getObject().getClass());
  }
}

