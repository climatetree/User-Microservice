
package com.climatetree.user.controller;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.climatetree.user.config.JwtTokenUtil;
import com.climatetree.user.dao.RoleUpdateRequestDao;
import com.climatetree.user.dao.UserDao;
import com.climatetree.user.dto.Execution;
import com.climatetree.user.enums.Constants;
import com.climatetree.user.enums.ResultEnum;
import com.climatetree.user.model.JwtRequest;
import com.climatetree.user.model.Role;
import com.climatetree.user.model.User;
import com.climatetree.user.service.JwtUserDetailsService;
import com.climatetree.user.service.RoleUpdateRequestService;
import com.climatetree.user.service.UserService;

@RunWith(SpringRunner.class)
public class UserControllerTest {

    private static final String AUTHORIZATION_REQUESTHEADE = "authorizationRequestHeader";

    private static final String TOKEN = AUTHORIZATION_REQUESTHEADE.substring(7);

    private User user = getUser();
    private User loggedUser = getLoggedUser();

    private UserDao mockUserDao = mock(UserDao.class);
    private JwtRequest mockJwtRequest = mock(JwtRequest.class);
    private JwtTokenUtil mockJwtTokenUtil = mock(JwtTokenUtil.class);
    private JwtUserDetailsService mockJwtUserDetailService = mock(JwtUserDetailsService.class);
    private HttpServletRequest mockedHttpServletRequest = Mockito.mock(HttpServletRequest.class);
    private RoleUpdateRequestService mockRoleUpdateRequestService = mock(RoleUpdateRequestService.class);
    private RoleUpdateRequestDao mockRoleUpdateRequestDao = mock(RoleUpdateRequestDao.class);

    private UserService mockUserService = new UserService(mockUserDao);
    private UserControllerV1 userController = new UserControllerV1(mockUserService, mockJwtUserDetailService, mockJwtTokenUtil,
            mockRoleUpdateRequestService);

    @Before
    public void init() throws Exception {
        when(mockJwtRequest.getUsername()).thenReturn("john");
        when(mockJwtRequest.getEmail()).thenReturn("john@gmail.com");
        when(mockJwtUserDetailService.loadUserByUsername(ArgumentMatchers.eq("john"),
                ArgumentMatchers.eq("john@gmail.com"))).thenReturn(loggedUser);
        when(mockedHttpServletRequest.getHeader("Authorization")).thenReturn(AUTHORIZATION_REQUESTHEADE);
        when(mockJwtTokenUtil.getUserFromToken(TOKEN)).thenReturn(loggedUser);
        when(mockJwtTokenUtil.getRoleFromToken(TOKEN)).thenReturn(new Role(1, "ADMIN"));
        when(mockUserDao.findByUserId(33L)).thenReturn(user);
    }

    @Test
    public void testDeleteUser() throws Exception {

        // when userId same of loggedUserId which is 20 in this case
        ResponseEntity<HttpStatus> unauthorizedResponseEntity = (ResponseEntity<HttpStatus>) userController
                .deleteUser(20L, mockedHttpServletRequest);
        assertTrue(unauthorizedResponseEntity.getStatusCode().value() == HttpStatus.UNAUTHORIZED.value());

        ResponseEntity<HttpStatus> successResponseEntity = (ResponseEntity<HttpStatus>) userController.deleteUser(33L,
                mockedHttpServletRequest);
        verify(mockUserDao).findByUserId(33L);
        assertTrue(successResponseEntity.getStatusCode().is2xxSuccessful());

        ResponseEntity<HttpStatus> databaseResponseEntity = (ResponseEntity<HttpStatus>) userController.deleteUser(22L,
                mockedHttpServletRequest);
        verify(mockUserDao).findByUserId(22L);
        assertTrue(databaseResponseEntity.getStatusCode().value() == HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void testBlacklistUser() throws Exception {
        // when userId same of loggedUserId which is 20 in this case
        ResponseEntity<HttpStatus> unauthorizedResponseEntity = (ResponseEntity<HttpStatus>) userController
                .blacklistUser(20L, mockedHttpServletRequest);
        assertTrue(unauthorizedResponseEntity.getStatusCode().value() == HttpStatus.UNAUTHORIZED.value());

        ResponseEntity<HttpStatus> successResponseEntity = (ResponseEntity<HttpStatus>) userController
                .blacklistUser(33L, mockedHttpServletRequest);
        verify(mockUserDao).findByUserId(33L);
        assertTrue(successResponseEntity.getStatusCode().is2xxSuccessful());

        ResponseEntity<HttpStatus> databaseResponseEntity = (ResponseEntity<HttpStatus>) userController
                .blacklistUser(22L, mockedHttpServletRequest);
        verify(mockUserDao).findByUserId(22L);
        assertTrue(databaseResponseEntity.getStatusCode().value() == HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void testUpdateUserRole() throws Exception {
        when(mockRoleUpdateRequestService.saveRoleUpdateRequest(33L, 1)).thenReturn(new Execution(ResultEnum.SUCCESS));

        ResponseEntity<HttpStatus> successResponseEntity = (ResponseEntity<HttpStatus>) userController
                . requestRoleUpdate(33L, 1);
        assertTrue(successResponseEntity.getStatusCode().is2xxSuccessful());

        when(mockRoleUpdateRequestService.saveRoleUpdateRequest(33L, 1))
                .thenReturn(new Execution(ResultEnum.FORBIDDEN));
        ResponseEntity<HttpStatus> forbiddenResponseEntity = (ResponseEntity<HttpStatus>) userController
                .requestRoleUpdate(33L, 1);
        assertTrue(forbiddenResponseEntity.getStatusCode().value() == HttpStatus.FORBIDDEN.value());

        verify(mockRoleUpdateRequestService, Mockito.times(2)).saveRoleUpdateRequest(33L, 1);

    }

    @Test
    public void testCreateUser() throws Exception {
        User user = new User();
        user.setEmail("abc");
        user.setUserId(43L);
        user.setNickname("tom");

        Execution<User> exe = new Execution<>();
        exe.setObject(user);
        exe.setResult(ResultEnum.SUCCESS);
        when(mockUserDao.save(user)).thenReturn(user);

        Map<String, Object> actualResultMap = userController.createUser(user);
        Assert.assertEquals(ResultEnum.SUCCESS, actualResultMap.get(Constants.SUCCESS.getStatusCode()));

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
 * private MockMvc mockMvc;
 *
 *
 *
 *
 *
 * @Test public void testFindUserById() {
 *
 * // setup UserService mockService = mock(UserService.class);
 * JwtUserDetailsService jwtService = mock(JwtUserDetailsService.class);
 * JwtTokenUtil jwtTokenUtil = mock(JwtTokenUtil.class);
 * RoleUpdateRequestService reqService = mock(RoleUpdateRequestService.class);
 * UserController controller = new UserController(mockService, jwtService,
 * jwtTokenUtil, reqService); User user = new User(); user.setEmail("abc");
 * user.setNickname("john"); user.setEmail("john@gmail.com");
 * user.setUserId(33L); user.setRole(new Role(1, "ADMIN"));
 *
 * Execution<User> exe = new Execution<>(); exe.setObject(user);
 *
 * exe.setResult(ResultEnum.SUCCESS);
 * when(mockService.insertUser(user)).thenReturn(exe); Map<String, Object> res =
 * null; try { res = controller.createUser(user); } catch (InternalException e)
 * { e.printStackTrace(); }
 *
 * // test verify(mockService).insertUser(user);
 * assertEquals(ResultEnum.SUCCESS, res.get(Constants.SUCCESS.getStatusCode()));
 * assertEquals(User.class, exe.getObject().getClass());
 */
/*
 * when(mockService.getUsersByRoleId(1)).thenReturn(exe); Map<String, Object>
 * resTwo = null; try { resTwo = controller.getUsersByRoleId(1); } catch
 * (InternalException e) { e.printStackTrace(); }
 * verify(mockService).getUsersByRoleId(1); assertEquals(ResultEnum.SUCCESS,
 * resTwo.get(Constants.SUCCESS.getStatusCode()));
 *//*
 *
 * assertEquals(User.class, exe.getObject().getClass()); }
 *
 * @Test public void findUserByEmail() throws InternalException { // setup
 * UserService mockService = mock(UserService.class); JwtUserDetailsService
 * jwtService = mock(JwtUserDetailsService.class); JwtTokenUtil jwtTokenUtil =
 * mock(JwtTokenUtil.class); RoleUpdateRequestService reqService =
 * mock(RoleUpdateRequestService.class); UserController controller = new
 * UserController(mockService, jwtService, jwtTokenUtil, reqService); User user
 * = new User(); user.setEmail("abc"); user.setNickname("john");
 * user.setEmail("john@gmail.com"); user.setUserId(33L); user.setRole(new
 * Role(1, "ADMIN"));
 *
 * Execution<User> exe = new Execution<>(); exe.setObject(user);
 *
 * exe.setResult(ResultEnum.SUCCESS);
 * when(mockService.insertUser(user)).thenReturn(exe); Map<String, Object> res =
 * controller.createUser(user);
 *
 * // test verify(mockService).insertUser(user);
 * assertEquals(ResultEnum.SUCCESS, res.get(Constants.SUCCESS.getStatusCode()));
 * assertEquals(User.class, exe.getObject().getClass());
 *
 * when(mockService.getUser(33L)).thenReturn(exe);
 *
 *
 * //this function is no longer needed . Deleted it form main application
 */
/*
 * exe.setObjects(Arrays.asList(user));
 * when(mockService.findAllUsers()).thenReturn(exe);
 *
 * Map<String, Object> resTwo = controller.getUsers();
 * verify(mockService).findAllUsers(); assertEquals(ResultEnum.SUCCESS,
 * resTwo.get(Constants.SUCCESS.getStatusCode()));
 *//*
 *
 * assertEquals(User.class, exe.getObject().getClass()); }
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 * }
 *
 */
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
