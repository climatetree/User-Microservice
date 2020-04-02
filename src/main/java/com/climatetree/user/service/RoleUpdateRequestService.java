package com.climatetree.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.climatetree.user.dao.RoleUpdateRequestDao;
import com.climatetree.user.dto.Execution;
import com.climatetree.user.enums.ResultEnum;
import com.climatetree.user.model.RoleUpdateRequest;

/**
 * The type User service.
 */
@Service
public class RoleUpdateRequestService {

	@Autowired
	private RoleUpdateRequestDao reqDao;

	public RoleUpdateRequestService() {
	}

	public RoleUpdateRequestService(RoleUpdateRequestDao dao) {
		this.reqDao = dao;
	}

	/**
	 * Insert user execution.
	 *
	 * @param user the user
	 * @return the execution
	 */
	public Execution<?> saveRoleUpdateRequest(Long userId, Integer roleId) {
		Execution<?> res;
		try {
			RoleUpdateRequest existingReq = reqDao.findByUserId(userId);
			if (existingReq == null) {
				reqDao.save(new RoleUpdateRequest(userId, roleId));
				res = new Execution<>(ResultEnum.SUCCESS, 1);
			} else {
				res = new Execution<>(ResultEnum.FORBIDDEN);
			}
		} catch (Exception e) {
			res = new Execution<>(ResultEnum.INNER_ERROR);
		}
		return res;
	}

	/**
	 * Insert user execution.
	 *
	 * @param user the user
	 * @return the execution
	 */
	public Execution<?> getAllRoleUpdateRequests() {
		Execution<?> res;
		try {
			List<RoleUpdateRequest> requests = reqDao.findAll();
			res = new Execution<>(ResultEnum.SUCCESS, requests);
		} catch (Exception e) {
			res = new Execution<>(ResultEnum.INNER_ERROR);
		}
		return res;
	}

}
