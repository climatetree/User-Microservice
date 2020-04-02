package com.climatetree.user.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "role_update_request", schema = "public")
public class RoleUpdateRequest {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "request_id", nullable = false)
	private Long requestId;

	private Long userId;

	private Integer roleId;

	public Long getRequestId() {
		return requestId;
	}

	public void setRequestId(Long requestId) {
		this.requestId = requestId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public RoleUpdateRequest(Long userId, Integer roleId) {
		this.userId = userId;
		this.roleId = roleId;
	}

	public RoleUpdateRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "RoleUpdateRequest [requestId=" + requestId + ", userId=" + userId + ", roleId=" + roleId + "]";
	}

}
