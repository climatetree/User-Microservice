package com.climatetree.user.service;

import static org.hamcrest.CoreMatchers.any;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.climatetree.user.dao.UserDao;
import com.climatetree.user.dto.Execution;
import com.climatetree.user.enums.ResultEnum;
import com.climatetree.user.model.Role;
import com.climatetree.user.model.User;

public class UserServiceTest {

	@Test
	void getUserEmail() throws Exception {
		UserDao mockDao = Mockito.mock(UserDao.class);
		UserService mockService = new UserService(mockDao);
		User newUser = new User();
		newUser.setEmail("tom@gmail.com");
		when(mockDao.findByUserId(1L)).thenReturn(newUser);

		Execution<User> exe = mockService.getUser(1L);
		User resUser = exe.getObject();

		assertEquals(User.class, resUser.getClass());
		assertEquals(ResultEnum.SUCCESS, exe.getResult());
		assertEquals("tom@gmail.com", resUser.getEmail());
	}

	@Test
	void getUserId() throws Exception {
		UserDao mockDao = Mockito.mock(UserDao.class);
		UserService mockService = new UserService(mockDao);
		User newUser = new User();
		newUser.setUserId(101L);
		when(mockDao.findByUserId(1L)).thenReturn(newUser);
		Execution<User> exe = mockService.getUser(1L);
		User resUser = exe.getObject();
		assertEquals(User.class, resUser.getClass());
		assertEquals(ResultEnum.SUCCESS, exe.getResult());
		assertEquals(101, resUser.getUserId().intValue());
	}

	@Test
	void getUserName() throws Exception {
		UserDao mockDao = Mockito.mock(UserDao.class);
		UserService mockService = new UserService(mockDao);
		User newUser = new User();
		newUser.setNickname("John");
		when(mockDao.findByUserId(1L)).thenReturn(newUser);

		Execution<User> exe = mockService.getUser(1L);
		User resUser = exe.getObject();
		assertEquals(User.class, resUser.getClass());
		assertEquals(ResultEnum.SUCCESS, exe.getResult());
		assertEquals("John", resUser.getNickname());
	}

	@Test
	void getUserRole() throws Exception {

		UserDao mockDao = Mockito.mock(UserDao.class);
		UserService mockService = new UserService(mockDao);
		User newUser = new User();
		newUser.setRole(new Role(2, "ADV_USERS"));
		when(mockDao.findByUserId(1L)).thenReturn(newUser);

		Execution<User> exe = mockService.getUser(1L);
		User resUser = exe.getObject();
		assertEquals(User.class, resUser.getClass());
		assertEquals(ResultEnum.SUCCESS, exe.getResult());
		assertEquals(2, resUser.getRole().getRoleId());
	}

	@Test
	void isUserBlacklisted() throws Exception {

		UserDao mockDao = Mockito.mock(UserDao.class);
		UserService mockService = new UserService(mockDao);
		User newUser = new User();
		newUser.setBlacklisted(true);
		when(mockDao.findByUserId(1L)).thenReturn(newUser);

		Execution<User> exe = mockService.getUser(1L);
		User resUser = exe.getObject();
		assertEquals(User.class, resUser.getClass());
		assertEquals(ResultEnum.SUCCESS, exe.getResult());
		assertEquals(true, resUser.getBlacklisted());
	}

	@Test
	void getRegistrationDate() throws Exception {
		UserDao mockDao = Mockito.mock(UserDao.class);
		UserService mockService = new UserService(mockDao);
		User newUser = new User();
		newUser.setRegistrationDate(new Date(2019 / 10 / 23));
		when(mockDao.findByUserId(1L)).thenReturn(newUser);

		Execution<User> exe = mockService.getUser(1L);
		User resUser = exe.getObject();
		assertEquals(User.class, resUser.getClass());
		assertEquals(ResultEnum.SUCCESS, exe.getResult());
		assertEquals(new Date(2019 / 10 / 23), resUser.getRegistrationDate());
	}

	@Test
	void getLastLoginTime() throws Exception {
		UserDao mockDao = Mockito.mock(UserDao.class);
		UserService mockService = new UserService(mockDao);
		User newUser = new User();
		newUser.setLastLoginTime(new Date(2020 / 18));
		when(mockDao.findByUserId(1L)).thenReturn(newUser);

		Execution<User> exe = mockService.getUser(1L);
		User resUser = exe.getObject();
		assertEquals(User.class, resUser.getClass());
		assertEquals(ResultEnum.SUCCESS, exe.getResult());
		assertEquals(new Date(2020 / 18), resUser.getLastLoginTime());
	}

	@Test
	public void getUserByEmailTest() {
		UserDao mockDao = Mockito.mock(UserDao.class);
		UserService mockService = new UserService(mockDao);
		User newUser = new User();
		newUser.setUserId(1L);
		when(mockDao.findByUserId(1L)).thenReturn(newUser);

		Execution<User> exe = mockService.getUser(1L);
		User resUser = exe.getObject();

		assertEquals(User.class, resUser.getClass());
		assertEquals(ResultEnum.SUCCESS, exe.getResult());
		assertEquals(1L, (long) resUser.getUserId());
		verify(mockDao).findByUserId((long) 1);
	}

	@Test
	public void testGetUserFailed1() {

		UserDao mockDao = Mockito.mock(UserDao.class);
		UserService mockService = new UserService(mockDao);
		Execution<User> res = mockService.getUser(999L);
		assertEquals(res.getResult(), ResultEnum.DATABASE_ERROR);
	}

	@Test
	public void testGetUserFailed2() {
		UserDao mockDao = Mockito.mock(UserDao.class);
		UserService mockService = new UserService(mockDao);
		when(mockDao.findByUserId(1L)).thenThrow(new RuntimeException("test exception"));
		Execution<User> res = mockService.getUser(1L);
		assertEquals(res.getResult(), ResultEnum.INNER_ERROR);
	}

	@Test
	public void testGetUserByEmail() {
		UserDao mockDao = Mockito.mock(UserDao.class);
		UserService mockService = new UserService(mockDao);
		User expectedUser = new User();
		expectedUser.setUserId(1L);
		expectedUser.setNickname("A");
		expectedUser.setEmail("a@a.com");
		when(mockDao.findByEmail("a@a.com")).thenReturn(expectedUser);
		Execution<User> res = mockService.getUserByEmail("a@a.com");
		assertEquals(ResultEnum.SUCCESS, res.getResult());
		assertEquals(expectedUser, res.getObject());
	}

	@Test
	public void testGetUserByEmailFailed1() {

		UserDao mockDao = Mockito.mock(UserDao.class);
		UserService mockService = new UserService(mockDao);
		User expectedUser = null;
		when(mockDao.findByEmail("a@a.com")).thenReturn(expectedUser);
		Execution<User> res = mockService.getUserByEmail("a@a.com");
		assertEquals(ResultEnum.DATABASE_ERROR, res.getResult());
	}

	@Test
	public void testGetUserByEmailFailed2() {
		UserDao mockDao = Mockito.mock(UserDao.class);
		UserService mockService = new UserService(mockDao);
		User expectedUser = new User();
		expectedUser.setEmail("a@a.com");
		when(mockDao.findByEmail("a@a.com")).thenThrow(new RuntimeException("test Exception"));
		Execution<User> res = mockService.getUserByEmail("a@a.com");
		assertEquals(ResultEnum.INNER_ERROR, res.getResult());
	}

	@Test
	public void testGetAllUsers() {
		UserDao mockDao = Mockito.mock(UserDao.class);
		UserService mockService = new UserService(mockDao);
		List<User> expectedResult = new ArrayList<>();
		User user1 = new User();
		user1.setUserId(1L);
		User user2 = new User();
		user2.setUserId(2L);
		expectedResult.add(user1);
		expectedResult.add(user2);
		when(mockDao.findAll()).thenReturn(expectedResult);
		Execution<User> res = mockService.findAllUsers();
		assertEquals(ResultEnum.SUCCESS, res.getResult());
		assertEquals(expectedResult, res.getObjects());
	}

	@Test
	public void testGetAllUsersFailed1() {
		UserDao mockDao = Mockito.mock(UserDao.class);
		UserService mockService = new UserService(mockDao);
		when(mockDao.findAll()).thenThrow(new RuntimeException("test Exception"));
		Execution<User> res = mockService.findAllUsers();
		assertEquals(ResultEnum.INNER_ERROR, res.getResult());
	}

	@Test
	public void testInsertUser() {
		UserDao mockDao = Mockito.mock(UserDao.class);
		UserService mockService = new UserService(mockDao);
		User expectedUser = new User();
		expectedUser.setUserId(1L);
		Execution<User> res = mockService.insertUser(expectedUser);
		assertEquals(ResultEnum.SUCCESS, res.getResult());
		assertEquals(1, res.getCount());
	}

	@Test
	public void testInsertUserFailed1() {
		// TODO the add method has to update
		UserDao mockDao = Mockito.mock(UserDao.class);
		UserService mockService = new UserService(mockDao);
		User expectedUser = new User();
		when(mockDao.save(expectedUser)).thenThrow(new RuntimeException("test Exception"));
		Execution<User> res = mockService.insertUser(expectedUser);
		assertEquals(ResultEnum.INNER_ERROR, res.getResult());
	}

	@Test
	public void testDeleteUser() {
		// TODO the delete method has to update
		UserDao mockDao = Mockito.mock(UserDao.class);
		when(mockDao.findByUserId(1L)).thenReturn(new User());
		UserService mockService = new UserService(mockDao);
		User expectedUser = new User();
		expectedUser.setUserId(1L);
		Execution<User> res = mockService.deleteUser(1L);
		assertEquals(ResultEnum.SUCCESS, res.getResult());
	}

	@Test
	public void testDeleteUserFailed1() {
		UserDao mockDao = Mockito.mock(UserDao.class);
		UserService mockService = new UserService(mockDao);
		doThrow(new RuntimeException()).when(mockDao).findByUserId(1L);
		Execution<User> res = mockService.deleteUser(1L);
		assertEquals(ResultEnum.INNER_ERROR, res.getResult());
	}

	@Test
	public void testGetUsersByName() {
		UserDao mockDao = Mockito.mock(UserDao.class);
		UserService mockService = new UserService(mockDao);
		List<User> expectedResult = new ArrayList<>();
		User user1 = new User();
		user1.setUserId(1L);
		User user2 = new User();
		user2.setUserId(2L);
		expectedResult.add(user1);
		expectedResult.add(user2);
		when(mockDao.findByNickname("test")).thenReturn(expectedResult);
		Execution<User> res = mockService.getUsersByName("test");
		assertEquals(ResultEnum.SUCCESS, res.getResult());
		assertEquals(expectedResult, res.getObjects());
	}

	@Test
	public void testGetUsersByNameFailed1() {
		UserDao mockDao = Mockito.mock(UserDao.class);
		UserService mockService = new UserService(mockDao);
		when(mockDao.findByNickname("test")).thenThrow(new RuntimeException("test Exception"));
		Execution<User> res = mockService.getUsersByName("test");
		assertEquals(ResultEnum.INNER_ERROR, res.getResult());
	}

	@Test
	public void testGetUsersByRoleId() {
		UserDao mockDao = Mockito.mock(UserDao.class);
		UserService mockService = new UserService(mockDao);
		List<User> expectedResult = new ArrayList<>();
		User user1 = new User();
		user1.setUserId(1L);
		user1.setRole(new Role(1, "ADMIN"));

		User user2 = new User();
		user2.setUserId(2L);
		user2.setRole(new Role(1, "ADMIN"));

		expectedResult.add(user1);
		expectedResult.add(user2);
		when(mockDao.findByNickname("test")).thenReturn(expectedResult);
		Execution<User> res = mockService.getUsersByName("test");
		assertEquals(ResultEnum.SUCCESS, res.getResult());
		assertEquals(expectedResult, res.getObjects());
	}

	@Test
	public void testGetUsersByRoleIdFailed1() {
		UserDao mockDao = Mockito.mock(UserDao.class);
		UserService mockService = new UserService(mockDao);
		when(mockDao.findByRoleId(1)).thenThrow(new RuntimeException("test Exception"));
		Execution<User> res = mockService.getUsersByRoleId(1);
		assertEquals(ResultEnum.INNER_ERROR, res.getResult());
	}
}