package com.climatetree.user.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.climatetree.user.config.JwtTokenUtil;
import com.climatetree.user.dto.Execution;
import com.climatetree.user.enums.Constants;
import com.climatetree.user.enums.ResultEnum;
import com.climatetree.user.exception.InternalException;
import com.climatetree.user.model.JwtRequest;
import com.climatetree.user.model.JwtResponse;
import com.climatetree.user.model.Role;
import com.climatetree.user.model.RoleUpdateRequest;
import com.climatetree.user.model.User;
import com.climatetree.user.service.JwtUserDetailsService;
import com.climatetree.user.service.RoleUpdateRequestService;
import com.climatetree.user.service.UserService;

/**
 * The UserController translates interaction with the front-end user interface
 * into actions to be performed by our "User" model.
 */
@RestController
@RequestMapping("/user")
public class UserController {

	// The userService component is the bridge between the controller and the DAO
	@Autowired
	private UserService userService;

	@Autowired
	private RoleUpdateRequestService reqService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService jwtService;

	public UserController() {
	}

	public UserController(UserService service, JwtUserDetailsService jwtService, JwtTokenUtil jwtTokenUtil, RoleUpdateRequestService reqService) {
		this.userService = service;
		this.jwtService = jwtService;
		this.jwtTokenUtil = jwtTokenUtil;
		this.reqService =reqService;
	}

	/**
	 * Find all users map.
	 *
	 * @return the map
	 */
	@GetMapping("")
	@ResponseBody
	public Map<String, Object> getUsers() throws InternalException {
		Map<String, Object> resultMap = new HashMap<>();
		try {
			Execution<User> res = userService.findAllUsers();
			resultMap.put(Constants.USER.getStatusCode(), res.getObjects());
			resultMap.put(Constants.SUCCESS.getStatusCode(), res.getResult());
		} catch (Exception e) {
			throw new InternalException(e.getMessage());
		}

		return resultMap;
	}

	/**
	 * Find users by name map.
	 *
	 * @param name the name
	 * @return the map
	 */
	@GetMapping("/searchname")
	@ResponseBody
	public Map<String, Object> getUsersByName(String name) throws InternalException {
		Map<String, Object> resultMap = new HashMap<>();
		try {
			Execution<User> res = userService.getUsersByName(name);
			resultMap.put(Constants.SUCCESS.getStatusCode(), res.getResult());
			resultMap.put(Constants.USER.getStatusCode(), res.getObjects());
		} catch (Exception e) {
			throw new InternalException(e.getMessage());
		}
		return resultMap;
	}

	/**
	 * Get all users based on their roles
	 *
	 * @param roleid the roleid
	 * @return map
	 */
	@GetMapping("/searchrole")
	@ResponseBody
	public Map<String, Object> getUsersByRoleId(Integer roleid) throws InternalException {
		Map<String, Object> resultMap = new HashMap<>();
		try {
			Execution<User> res = userService.getUsersByRoleId(roleid);
			resultMap.put(Constants.SUCCESS.getStatusCode(), res.getResult());
			resultMap.put(Constants.USER.getStatusCode(), res.getObjects());
		} catch (Exception e) {
			throw new InternalException(e.getMessage());

		}
		return resultMap;
	}

	/**
	 * add a new user
	 *
	 * @param newUser the new user
	 * @return map
	 */
	@PostMapping("/addUser")
	@ResponseBody
	public Map<String, Object> createUser(@RequestBody User newUser) throws InternalException {
		Map<String, Object> resultMap = new HashMap<>();
		try {
			Execution<User> res = userService.insertUser(newUser);
			resultMap.put(Constants.SUCCESS.getStatusCode(), res.getResult());
			resultMap.put(Constants.USER.getStatusCode(), res.getObjects());
		} catch (Exception e) {
			throw new InternalException(e.getMessage());

		}
		return resultMap;
	}

	/**
	 * find all users that has been flagged (true) --> to moderate
	 *
	 * @return a list of user that has been been flagged
	 */
	@GetMapping("/flagged_users")
	public Map<String, Object> getBlacklistedUsers() throws InternalException {
		Map<String, Object> resultMap = new HashMap<>();
		try {
			Execution<User> res = userService.getBlacklistedUsers();
			resultMap.put(Constants.USER.getStatusCode(), res.getObjects());
		} catch (Exception e) {
			throw new InternalException(e.getMessage());
		}
		return resultMap;
	}

	@RequestMapping(value = "/setup_roles", method = RequestMethod.POST)
	public void setupRoles() {
		userService.setupRoles();
	}

	/**
	 * delete a user
	 *
	 * @param userId the user id
	 * @return map
	 */
	@RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteUser(@RequestBody JwtRequest authenticationRequest, @PathVariable Long userId)
			throws UnsupportedEncodingException {

		final User loggedUser = jwtService.loadUserByUsername(authenticationRequest.getUsername(),
				authenticationRequest.getEmail());
		if (loggedUser.getUserId().equals(userId)) {
			return new ResponseEntity(HttpStatus.UNAUTHORIZED);
		}
		final String token = jwtTokenUtil.generateToken(loggedUser);
		Role role = jwtTokenUtil.getRoleFromToken(token);
		if (role != null && role.getName().equals(Constants.ADMIN.name())) {
			Execution<User> res = userService.deleteUser(userId);
			if (res.getResult().equals(ResultEnum.DATABASE_ERROR)) {
				return new ResponseEntity(HttpStatus.NOT_FOUND);
			} else {
				return new ResponseEntity(HttpStatus.OK);
			}
		}
		return new ResponseEntity(HttpStatus.UNAUTHORIZED);
	}

	/**
	 * Update user role map.
	 *
	 * @param request the request
	 * @return the map
	 */
	@RequestMapping(value = "/{userId}/{roleId}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateUser(@RequestBody JwtRequest authenticationRequest, @PathVariable Long userId,
										@PathVariable Integer roleId) throws UnsupportedEncodingException {

		final User loggedUser = jwtService.loadUserByUsername(authenticationRequest.getUsername(),
				authenticationRequest.getEmail());
		if (loggedUser.getUserId().equals(userId)) {
			return new ResponseEntity(HttpStatus.UNAUTHORIZED);
		}
		final String token = jwtTokenUtil.generateToken(loggedUser);
		Role role = jwtTokenUtil.getRoleFromToken(token);
		if (role != null && role.getName().equals(Constants.ADMIN.name())) {
			Execution<User> res = userService.updateUser(userId, roleId);
			if (res.getResult().equals(ResultEnum.DATABASE_ERROR)) {
				return new ResponseEntity(HttpStatus.NOT_FOUND);
			} else if (res.getResult().equals(ResultEnum.FORBIDDEN)) {
				return new ResponseEntity(HttpStatus.FORBIDDEN);
			} else {
				return new ResponseEntity(HttpStatus.OK);
			}
		}
		return new ResponseEntity(HttpStatus.UNAUTHORIZED);
	}

	/**
	 * Method to black-list a given user (based on it's userid)
	 * @param authenticationRequest JwtRequest
	 * @param userId the id of the user to delete
	 * @return a HTTP response (ok, user not found or not authorized)
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "blacklist/{userId}", method = RequestMethod.PUT)
	public ResponseEntity<?> blacklistUser(@RequestBody JwtRequest authenticationRequest, @PathVariable Long userId)
			throws UnsupportedEncodingException {

		final User loggedUser = jwtService.loadUserByUsername(authenticationRequest.getUsername(),
				authenticationRequest.getEmail());
		if (loggedUser.getUserId().equals(userId)) {
			return new ResponseEntity(HttpStatus.UNAUTHORIZED);
		}
		final String token = jwtTokenUtil.generateToken(loggedUser);
		Role role = jwtTokenUtil.getRoleFromToken(token);
		if (role != null && (role.getName().equals(Constants.ADMIN.name())
				|| role.getName().equals(Constants.MODERATOR.name()))) {
			Execution<User> res = userService.blacklistUser(userId);
			if (res.getResult().equals(ResultEnum.DATABASE_ERROR)) {
				return new ResponseEntity(HttpStatus.NOT_FOUND);
			} else {
				return new ResponseEntity(HttpStatus.OK);
			}
		}
		return new ResponseEntity(HttpStatus.UNAUTHORIZED);
	}

	@RequestMapping(value = "/request_role_change/{userId}/{roleId}", method = RequestMethod.POST)
	public ResponseEntity<?> requestRoleUpdate(@RequestBody JwtRequest authenticationRequest, @PathVariable Long userId,
											   @PathVariable Integer roleId) {
		Execution<?> res = reqService.saveRoleUpdateRequest(userId, roleId);
		if (res.getResult().equals(ResultEnum.FORBIDDEN)) {
			return new ResponseEntity(HttpStatus.FORBIDDEN);
		} else {
			return new ResponseEntity(HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/get_all_role_update_requests", method = RequestMethod.GET)
	public Map<String, Object> getAllRoleUpdateRequests(@RequestBody JwtRequest authenticationRequest)
			throws UnsupportedEncodingException {
		Map<String, Object> resultMap = new HashMap<>();
		final User loggedUser = jwtService.loadUserByUsername(authenticationRequest.getUsername(),
				authenticationRequest.getEmail());
		final String token = jwtTokenUtil.generateToken(loggedUser);
		Role role = jwtTokenUtil.getRoleFromToken(token);
		if (role != null && role.getName().equals(Constants.ADMIN.name())) {
			Execution<?> res = reqService.getAllRoleUpdateRequests();
			resultMap.put(Constants.REQUESTS.getStatusCode(), res.getObjects());
		}
		return resultMap;
	}

}
