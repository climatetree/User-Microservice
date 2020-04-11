package com.climatetree.user.controller;

import com.climatetree.user.config.JwtTokenUtil;
import com.climatetree.user.dto.Execution;
import com.climatetree.user.enums.Constants;
import com.climatetree.user.enums.ResultEnum;
import com.climatetree.user.exception.InternalException;
import com.climatetree.user.model.JwtRequest;
import com.climatetree.user.model.Role;
import com.climatetree.user.model.User;
import com.climatetree.user.service.JwtUserDetailsService;
import com.climatetree.user.service.RoleUpdateRequestService;
import com.climatetree.user.service.UserService;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * The UserController translates interaction with the front-end user interface into actions to be
 * performed by our "User" model.
 */
@RestController
@RequestMapping("v1/user")
public class UserControllerV1 {

	// The userService component is the bridge between the controller and the DAO
	@Autowired
	private UserService userService;

	@Autowired
	private RoleUpdateRequestService reqService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
  JwtUserDetailsService jwtService;

	public UserControllerV1() {
	}

	public UserControllerV1(
      UserService service, JwtUserDetailsService jwtService, JwtTokenUtil jwtTokenUtil, RoleUpdateRequestService reqService) {
		this.userService = service;
		this.jwtService = jwtService;
		this.jwtTokenUtil = jwtTokenUtil;
		this.reqService =reqService;
	}
	/**
	 * Instantiates a new User controller.
	 *
	 * @param service the service
	 */
	public UserControllerV1(UserService service) {
		this.userService = service;
	}

	/**
	 * Find users by name map.
	 *
	 * @param authenticationRequest the authentication request
	 * @return the map
	 * @throws InternalException the internal exception
	 */
	@GetMapping("/searchname")
	@ResponseBody
	public Map<String, Object> getUsersByName(@RequestBody JwtRequest authenticationRequest) throws InternalException {
		Map<String, Object> resultMap = new HashMap<>();
		try {
			Execution<User> res = userService.getUsersByName(authenticationRequest.getUsername());
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
	 * @param authenticationRequest the authentication request
	 * @return map users by role id
	 * @throws InternalException the internal exception
	 */
	@GetMapping("/searchrole")
	@ResponseBody
	public Map<String, Object> getUsersByRoleId(@RequestBody JwtRequest authenticationRequest) throws InternalException {
		Map<String, Object> resultMap = new HashMap<>();
		try {
			Execution<User> res = userService.getUsersByRoleId(authenticationRequest.getRoleId());
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
	 * @param authenticationRequest the authentication request
	 * @return map users by email id
	 * @throws InternalException the internal exception
	 */
	@GetMapping("/searchemail")
	@ResponseBody
	public Map<String, Object> getUsersByEmailId(@RequestBody JwtRequest authenticationRequest) throws InternalException {
		Map<String, Object> resultMap = new HashMap<>();
		try {
			Execution<User> res = userService.getUserByEmail(authenticationRequest.getEmail());
			resultMap.put(Constants.SUCCESS.getStatusCode(), res.getResult());
			resultMap.put(Constants.USER.getStatusCode(), res.getObject());
		} catch (Exception e) {
			throw new InternalException(e.getMessage());

		}
		return resultMap;
	}

	/**
	 * add a new user
	 *
	 * @param newUser the new user
	 * @return map map
	 * @throws InternalException the internal exception
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
	 * @throws InternalException the internal exception
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

	/**
	 * Sets roles.
	 */
	@RequestMapping(value = "/setup_roles", method = RequestMethod.POST)
	public void setupRoles() {
		userService.setupRoles();
	}

	/**
	 * delete a user
	 *
	 * @param userId the user id
	 * @param req    the req
	 * @return map response entity
	 * @throws UnsupportedEncodingException the unsupported encoding exception
	 */
	@RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteUser(@PathVariable Long userId, HttpServletRequest req)
			throws UnsupportedEncodingException {
		String token = req.getHeader("Authorization").substring(7);
		User loggedUser = jwtTokenUtil.getUserFromToken(token);
		if (loggedUser.getUserId().equals(userId)) {
			return new ResponseEntity(HttpStatus.UNAUTHORIZED);
		}
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
	 * @param req    the req
	 * @param userId                the user id
	 * @param roleId                the role id
	 * @return the map
	 * @throws UnsupportedEncodingException the unsupported encoding exception
	 */
	@RequestMapping(value = "/{userId}/{roleId}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateUser(@PathVariable Long userId,
			@PathVariable Integer roleId,HttpServletRequest req) throws UnsupportedEncodingException {

		String token = req.getHeader("Authorization").substring(7);
		User loggedUser = jwtTokenUtil.getUserFromToken(token);
		if (loggedUser.getUserId().equals(userId)) {
			return new ResponseEntity(HttpStatus.UNAUTHORIZED);
		}
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
	 * Blacklist user response entity.
	 *
	 * @param req    the req
	 * @param userId                the user id
	 * @return the response entity
	 * @throws UnsupportedEncodingException the unsupported encoding exception
	 */
	@RequestMapping(value = "blacklist/{userId}", method = RequestMethod.PUT)
	public ResponseEntity<?> blacklistUser( @PathVariable Long userId,HttpServletRequest req)
			throws UnsupportedEncodingException {

		String token = req.getHeader("Authorization").substring(7);
		User loggedUser = jwtTokenUtil.getUserFromToken(token);
		if (loggedUser.getUserId().equals(userId)) {
			return new ResponseEntity(HttpStatus.UNAUTHORIZED);
		}
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

	/**
	 * UnBlacklist user response entity.
	 *
	 * @param req    the req
	 * @param userId                the user id
	 * @return the response entity
	 * @throws UnsupportedEncodingException the unsupported encoding exception
	 */
	@RequestMapping(value = "unblacklist/{userId}", method = RequestMethod.PUT)
	public ResponseEntity<?> unblacklistUser( @PathVariable Long userId,HttpServletRequest req)
			throws UnsupportedEncodingException {
		String token = req.getHeader("Authorization").substring(7);
		User loggedUser = jwtTokenUtil.getUserFromToken(token);
		if (loggedUser.getUserId().equals(userId)) {
			return new ResponseEntity(HttpStatus.UNAUTHORIZED);
		}
		Role role = jwtTokenUtil.getRoleFromToken(token);
		if (role != null && (role.getName().equals(Constants.ADMIN.name())
				|| role.getName().equals(Constants.MODERATOR.name()))) {
			Execution<User> res = userService.unblacklistUser(userId);
			if (res.getResult().equals(ResultEnum.DATABASE_ERROR)) {
				return new ResponseEntity(HttpStatus.NOT_FOUND);
			} else {
				return new ResponseEntity(HttpStatus.OK);
			}
		}
		return new ResponseEntity(HttpStatus.UNAUTHORIZED);
	}




	/**
	 * Request role update response entity.
	 *
	 *
	 * @param userId                the user id
	 * @param roleId                the role id
	 * @return the response entity
	 */
	@RequestMapping(value = "/request_role_change/{userId}/{roleId}", method = RequestMethod.POST)
	public ResponseEntity<?> requestRoleUpdate(@PathVariable Long userId,
			@PathVariable Integer roleId) {
		Execution<?> res = reqService.saveRoleUpdateRequest(userId, roleId);
		if (res.getResult().equals(ResultEnum.FORBIDDEN)) {
			return new ResponseEntity(HttpStatus.FORBIDDEN);
		} else {
			return new ResponseEntity(HttpStatus.OK);
		}
	}

	/**
	 * Gets all role update requests.
	 *
	 * @param req    the req
	 * @return the all role update requests
	 * @throws UnsupportedEncodingException the unsupported encoding exception
	 */
	@RequestMapping(value = "/get_all_role_update_requests", method = RequestMethod.GET)
	public Map<String, Object> getAllRoleUpdateRequests(HttpServletRequest req)
			throws UnsupportedEncodingException {
		Map<String, Object> resultMap = new HashMap<>();
		String token = req.getHeader("Authorization").substring(7);
		Role role = jwtTokenUtil.getRoleFromToken(token);
		if (role != null && role.getName().equals(Constants.ADMIN.name())) {
			Execution<?> res = reqService.getAllRoleUpdateRequests();
			resultMap.put(Constants.REQUESTS.getStatusCode(), res.getObjects());
		}
		return resultMap;
	}
}
