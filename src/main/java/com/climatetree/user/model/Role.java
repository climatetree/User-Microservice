package com.climatetree.user.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/*
Object representation of the ROLE table from the database. Setters and getters + constructors.
 */
@Entity
@Table(name = "role", schema = "public")
public class Role {

	@Id
	@Column(name = "roleid", nullable = false)
	private int roleId;
	private String name;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "role")
	@JsonIgnore
	private Set<User> users;

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int id) {
		this.roleId = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public Role(int roleId, String name) {
		this.roleId = roleId;
		this.name = name;
		this.users = new HashSet<>();
	}

	@Override
	public String toString() {
		return "Role [roleId=" + roleId + ", name=" + name + ", users count=" + users.size() + "]";
	}

}
