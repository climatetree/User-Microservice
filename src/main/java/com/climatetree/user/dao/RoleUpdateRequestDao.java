package com.climatetree.user.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.climatetree.user.model.RoleUpdateRequest;

/**
 * The interface User dao.
 */
@Repository
public interface RoleUpdateRequestDao extends JpaRepository<RoleUpdateRequest, Long> {
	/**
	 * get all users in the user table.
	 * 
	 * @return A List of User objects.
	 */
	List<RoleUpdateRequest> findAll();

	/**
	 * Get a single User object by userId.
	 *
	 * @param userId A Long object userId.
	 * @return A User object.
	 */
	RoleUpdateRequest findByRequestId(Long requestId);
	
	/**
	 * Get a single User object by userId.
	 *
	 * @param userId A Long object userId.
	 * @return A User object.
	 */
	RoleUpdateRequest findByUserId(Long requestId);

	RoleUpdateRequest save(RoleUpdateRequest request);

}