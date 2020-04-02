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

/**
 * The type User.
 */
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

	private Boolean blacklisted;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "roleid")
	private Role role;

	@Column(name = "creation_date")
	private Date registrationDate;

	@Column(name = "last_login_date")
	private Date lastLoginTime;

	@Column(name = "last_login_location")
	private String lastLoginLocation;

	/**
	 * Instantiates a new User.
	 */
	public User() {
	}

	/**
	 * Instantiates a new User.
	 *
	 * @param user the user
	 */
	public User(User user) {
		this.email = user.getEmail();
		this.nickname = user.getNickname();
		this.lastLoginTime = user.lastLoginTime;
		this.role = user.role;
		this.userId = user.userId;
		this.registrationDate = user.registrationDate;
	}

	/**
	 * Instantiates a new User.
	 *
	 * @param email    the email
	 * @param nickname the nickname
	 */
	public User(String email, String nickname) {
		this.email = email;
		this.nickname = nickname;
	}

	/**
	 * Gets user id.
	 *
	 * @return the user id
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * Sets user id.
	 *
	 * @param userId the user id
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * Gets email.
	 *
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets email.
	 *
	 * @param email the email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Gets nickname.
	 *
	 * @return the nickname
	 */
	public String getNickname() {
		return nickname;
	}

	/**
	 * Sets nickname.
	 *
	 * @param nickname the nickname
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	/**
	 * Gets blacklisted.
	 *
	 * @return the blacklisted
	 */
	public Boolean getBlacklisted() {
		return blacklisted;
	}

	/**
	 * Sets blacklisted.
	 *
	 * @param blacklisted the blacklisted
	 */
	public void setBlacklisted(Boolean blacklisted) {
		this.blacklisted = blacklisted;
	}

	/**
	 * Gets role.
	 *
	 * @return the role
	 */
	public Role getRole() {
		return role;
	}

	/**
	 * Sets role.
	 *
	 * @param role the role
	 */
	public void setRole(Role role) {
		this.role = role;
	}

	/**
	 * Gets registration date.
	 *
	 * @return the registration date
	 */
	public Date getRegistrationDate() {
		return registrationDate;
	}

	/**
	 * Sets registration date.
	 *
	 * @param registrationDate the registration date
	 */
	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	/**
	 * Gets last login time.
	 *
	 * @return the last login time
	 */
	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	/**
	 * Sets last login time.
	 *
	 * @param lastLoginTime the last login time
	 */
	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	/**
	 * Gets last login location.
	 *
	 * @return the last login location
	 */
	public String getLastLoginLocation() {
		return lastLoginLocation;
	}

	/**
	 * Sets last login location.
	 *
	 * @param lastLoginLocation the last login location
	 */
	public void setLastLoginLocation(String lastLoginLocation) {
		this.lastLoginLocation = lastLoginLocation;
	}

	@Override
	public String toString() {
		return "User{" + "userId=" + userId + ", email='" + email + '\'' + ", nickname='" + nickname + '\''
				+ ", blacklisted=" + blacklisted + ", role=" + role + ", registrationDate=" + registrationDate
				+ ", lastLoginTime=" + lastLoginTime + ", lastLoginLocation='" + lastLoginLocation + '\'' + '}';
	}
}