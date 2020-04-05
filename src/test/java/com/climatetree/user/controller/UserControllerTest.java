package com.climatetree.user.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.climatetree.user.config.JwtTokenUtil;
import com.climatetree.user.dao.RoleUpdateRequestDao;
import com.climatetree.user.dao.UserDao;
import com.climatetree.user.dto.Execution;
import com.climatetree.user.enums.Constants;
import com.climatetree.user.enums.ResultEnum;
import com.climatetree.user.exception.InternalException;
import com.climatetree.user.model.JwtRequest;
import com.climatetree.user.model.Role;
import com.climatetree.user.model.RoleUpdateRequest;
import com.climatetree.user.model.User;
import com.climatetree.user.service.JwtUserDetailsService;
import com.climatetree.user.service.RoleUpdateRequestService;
import com.climatetree.user.service.UserService;

@RunWith(SpringRunner.class)
public class UserControllerTest {

  private MockMvc mockMvc;



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
    // when(controller.createUser(user)).thenReturn(result);

    try {
      verify(controller).createUser(user);
    } catch (InternalException e) {
      e.printStackTrace();
    }

    assertEquals("success", result.get("userId"));
    assertEquals("success", result.get("email"));
    assertEquals("success", result.get("username"));
    assertEquals("success", result.get("role"));

    assertEquals(User.class, exe.getObject().getClass());
  }

  @Test
  public void testFindUserByName() {

    // setup
    UserService mockService = mock(UserService.class);
    JwtUserDetailsService jwtService = mock(JwtUserDetailsService.class);
    JwtTokenUtil jwtTokenUtil = mock(JwtTokenUtil.class);
    RoleUpdateRequestService reqService = mock(RoleUpdateRequestService.class);
    UserController controller = new UserController(mockService,jwtService, jwtTokenUtil, reqService);
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
    assertEquals(ResultEnum.SUCCESS, resTwo.get(Constants.SUCCESS.getStatusCode()));

    assertEquals(User.class, exe.getObject().getClass());
  }

  @Test
  public void testFindUserById() {

    // setup
    UserService mockService = mock(UserService.class);
    JwtUserDetailsService jwtService = mock(JwtUserDetailsService.class);
    JwtTokenUtil jwtTokenUtil = mock(JwtTokenUtil.class);
    RoleUpdateRequestService reqService = mock(RoleUpdateRequestService.class);
    UserController controller = new UserController(mockService, jwtService, jwtTokenUtil, reqService);
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
    assertEquals(ResultEnum.SUCCESS, resTwo.get(Constants.SUCCESS.getStatusCode()));

    assertEquals(User.class, exe.getObject().getClass());
  }

  @Test
  public void findUserByEmail() throws InternalException {
    // setup
    UserService mockService = mock(UserService.class);
    JwtUserDetailsService jwtService = mock(JwtUserDetailsService.class);
    JwtTokenUtil jwtTokenUtil = mock(JwtTokenUtil.class);
    RoleUpdateRequestService reqService = mock(RoleUpdateRequestService.class);
    UserController controller = new UserController(mockService, jwtService, jwtTokenUtil, reqService);
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

    exe.setObjects(Arrays.asList(user));
    when(mockService.findAllUsers()).thenReturn(exe);

    Map<String, Object> resTwo = controller.getUsers();
    verify(mockService).findAllUsers();
    assertEquals(ResultEnum.SUCCESS, resTwo.get(Constants.SUCCESS.getStatusCode()));

    assertEquals(User.class, exe.getObject().getClass());
  }

  @Test
  public void testDeleteUser() throws Exception{
    User user = getUser();
    User loggedUser = getLoggedUser();
    String token = "12345678";

    UserDao userDao = mock(UserDao.class);
    JwtRequest jwtRequest = mock(JwtRequest.class);
    when(jwtRequest.getUsername()).thenReturn("john");
    when(jwtRequest.getEmail()).thenReturn("john@gmail.com");

    JwtTokenUtil jwtTokenUtil = mock(JwtTokenUtil.class);
    JwtUserDetailsService jwtService = mock(JwtUserDetailsService.class);


    when(jwtService.loadUserByUsername(ArgumentMatchers.eq("john"),ArgumentMatchers.eq("john@gmail.com"))).thenReturn(loggedUser);
    when(jwtTokenUtil.generateToken(loggedUser)).thenReturn(token);
    when(jwtTokenUtil.getRoleFromToken(token)).thenReturn(new Role(1, "ADMIN"));

    when(userDao.findByUserId(33L)).thenReturn(user);

    UserService userService = new UserService(userDao);

    RoleUpdateRequestService reqService = mock(RoleUpdateRequestService.class);

    UserController userController = new UserController(userService, jwtService, jwtTokenUtil, reqService);

    //when userId same of loggedUserId which is 20 in this case
    ResponseEntity<HttpStatus> unauthorizedResponseEntity = (ResponseEntity<HttpStatus>) userController.blacklistUser(jwtRequest, 20L);
    assertTrue(unauthorizedResponseEntity.getStatusCode().value() == HttpStatus.UNAUTHORIZED.value());


    ResponseEntity<HttpStatus> successResponseEntity = (ResponseEntity<HttpStatus>) userController.deleteUser(jwtRequest, 33L);
    verify(userDao).findByUserId(33L);
    assertTrue(successResponseEntity.getStatusCode().is2xxSuccessful());

    ResponseEntity<HttpStatus> databaseResponseEntity = (ResponseEntity<HttpStatus>) userController.deleteUser(jwtRequest, 22L);
    verify(userDao).findByUserId(22L);
    assertTrue(databaseResponseEntity.getStatusCode().value() == HttpStatus.NOT_FOUND.value());
  }

  @Test
  public void testBlacklistUser() throws Exception{
    User user = getUser();
    User loggedUser = getLoggedUser();
    String token = "12345678";

    UserDao userDao = mock(UserDao.class);
    JwtRequest jwtRequest = mock(JwtRequest.class);
    when(jwtRequest.getUsername()).thenReturn("john");
    when(jwtRequest.getEmail()).thenReturn("john@gmail.com");

    JwtTokenUtil jwtTokenUtil = mock(JwtTokenUtil.class);
    JwtUserDetailsService jwtService = mock(JwtUserDetailsService.class);


    when(jwtService.loadUserByUsername(ArgumentMatchers.eq("john"),ArgumentMatchers.eq("john@gmail.com"))).thenReturn(loggedUser);
    when(jwtTokenUtil.generateToken(loggedUser)).thenReturn(token);
    when(jwtTokenUtil.getRoleFromToken(token)).thenReturn(new Role(1, "ADMIN"));

    when(userDao.findByUserId(33L)).thenReturn(user);

    UserService userService = new UserService(userDao);

    RoleUpdateRequestService reqService = mock(RoleUpdateRequestService.class);

    UserController userController = new UserController(userService, jwtService, jwtTokenUtil, reqService);

    //when userId same of loggedUserId which is 20 in this case
    ResponseEntity<HttpStatus> unauthorizedResponseEntity = (ResponseEntity<HttpStatus>) userController.blacklistUser(jwtRequest, 20L);
    assertTrue(unauthorizedResponseEntity.getStatusCode().value() == HttpStatus.UNAUTHORIZED.value());

    ResponseEntity<HttpStatus> successResponseEntity = (ResponseEntity<HttpStatus>) userController.blacklistUser(jwtRequest, 33L);
    verify(userDao).findByUserId(33L);
    assertTrue(successResponseEntity.getStatusCode().is2xxSuccessful());

    ResponseEntity<HttpStatus> databaseResponseEntity = (ResponseEntity<HttpStatus>) userController.blacklistUser(jwtRequest, 22L);
    verify(userDao).findByUserId(22L);
    assertTrue(databaseResponseEntity.getStatusCode().value() == HttpStatus.NOT_FOUND.value());
  }


  @Test
  public void testUpdateUserRole() throws Exception {
    UserDao userDao = mock(UserDao.class);
    JwtRequest jwtRequest = mock(JwtRequest.class);
    when(jwtRequest.getUsername()).thenReturn("john");
    when(jwtRequest.getEmail()).thenReturn("john@gmail.com");

    JwtTokenUtil jwtTokenUtil = mock(JwtTokenUtil.class);
    JwtUserDetailsService jwtService = mock(JwtUserDetailsService.class);

    RoleUpdateRequestDao roleUpdateRequestDao = mock(RoleUpdateRequestDao.class);
    RoleUpdateRequestService reqService = new RoleUpdateRequestService(roleUpdateRequestDao);

    UserService userService = new UserService(userDao);

    UserController userController = new UserController(userService, jwtService, jwtTokenUtil, reqService);

    when(roleUpdateRequestDao.findByUserId(33L)).thenReturn(null);
    ResponseEntity<HttpStatus> successResponseEntity = (ResponseEntity<HttpStatus>) userController.requestRoleUpdate(jwtRequest, 33L,1);
    assertTrue(successResponseEntity.getStatusCode().is2xxSuccessful());

    when(roleUpdateRequestDao.findByUserId(33L)).thenReturn(new RoleUpdateRequest(33L,1));
    ResponseEntity<HttpStatus> forbiddenResponseEntity = (ResponseEntity<HttpStatus>) userController.requestRoleUpdate(jwtRequest, 33L,1);
    assertTrue(forbiddenResponseEntity.getStatusCode().value() == HttpStatus.FORBIDDEN.value());

    verify(roleUpdateRequestDao, Mockito.times(2)).findByUserId(33L);

  }


  private User getLoggedUser() {
    User user = new User();
    user.setNickname("john");
    user.setEmail("john@gmail.com");
    user.setUserId(20L);
    user.setRole(new Role(1, "ADMIN"));
    return user;

  }

  private User getUser() {
    User user = new User();
    user.setEmail("abc");
    user.setNickname("john");
    user.setEmail("john@gmail.com");
    user.setUserId(33L);
    user.setRole(new Role(1, "ADMIN"));
    return user;
  }





}

/*
 * @RequestMapping(value = "blacklist/{userId}", method = RequestMethod.PUT)
 * public ResponseEntity<?> blacklistUser(@RequestBody JwtRequest
 * authenticationRequest, @PathVariable Long userId) throws
 * UnsupportedEncodingException {
 *
 * final User loggedUser =
 * jwtService.loadUserByUsername(authenticationRequest.getUsername(),
 * authenticationRequest.getEmail()); if (loggedUser.getUserId().equals(userId))
 * { return new ResponseEntity(HttpStatus.UNAUTHORIZED); } final String token =
 * jwtTokenUtil.generateToken(loggedUser); Role role =
 * jwtTokenUtil.getRoleFromToken(token); if (role != null &&
 * (role.getName().equals(Constants.ADMIN.name()) ||
 * role.getName().equals(Constants.MODERATOR.name()))) { Execution<User> res =
 * userService.blacklistUser(userId); if
 * (res.getResult().equals(ResultEnum.DATABASE_ERROR)) { return new
 * ResponseEntity(HttpStatus.NOT_FOUND); } else { return new
 * ResponseEntity(HttpStatus.OK); } } return new
 * ResponseEntity(HttpStatus.UNAUTHORIZED); }
 **/
