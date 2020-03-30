package com.climatetree.user.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/*
Object representation of the USER table from the database. Setters and getters + constructors.
 */
@Entity
@Table(name = "user", schema = "public")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "userid", nullable = false)
	private Long userId;

	@Column(name = "email")
	private String email;

	@Column(name = "nickname")
	private String nickname;

	@Column(name = "flag")
	private Boolean flag;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "roleid")
	private Role role;

	@Column(name = "creation_date")
	private Date registrationDate;

	@Column(name = "last_login_date")
	private Date lastLoginTime;

	@Column(name = "last_login_location")
	private String lastLoginLocation;

	public User() {
	}

	public User(User user) {
		this.email = user.getEmail();
		this.nickname = user.getNickname();
		this.lastLoginTime = user.lastLoginTime;
		this.role = user.role;
		this.userId = user.userId;
		this.registrationDate = user.registrationDate;
	}

	public User(String email, String nickname) {
		this.email = email;
		this.nickname = nickname;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Boolean getFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getLastLoginLocation() {
		return lastLoginLocation;
	}

	public void setLastLoginLocation(String lastLoginLocation) {
		this.lastLoginLocation = lastLoginLocation;
	}

	@Override
	public String toString() {
		return "User{" + "userId=" + userId + ", email='" + email + '\'' + ", nickname='" + nickname + '\'' + ", flag="
				+ flag + ", role=" + role.getName() + ", registrationDate=" + registrationDate + ", lastLoginTime="
				+ lastLoginTime + ", lastLoginLocation='" + lastLoginLocation + '\'' + '}';
	}
}