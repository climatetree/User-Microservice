package com.CS6510.microservice.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/*
Object representation of the USER table from the database. Setters and getters + constructors.
 */
@Entity
@Table(name = "\"USER\"")
public class User {
  @Id
  @Column(name = "user_id")
  private Long userId;

  @Column(name = "email")
  private String email;

  @Column(name = "nickname")
  private String nickname;

  @Column(name = "flag")
  private Integer flag;

  @Column(name = "role_id")
  private Long roleId;

  @Column(name = "creation_date")
  private Date registrationDate;

  @Column(name = "last_login_date")
  private Date lastLoginTime;

  @Column(name = "last_login_location")
  private String lastLoginLocation;

  public User(Long userId, String email, String nickname, Integer flag, Long roleId, Date registrationDate, Date lastLoginTime, String lastLoginLocation) {
    this.userId = userId;
    this.email = email;
    this.nickname = nickname;
    this.flag = flag;
    this.roleId = roleId;
    this.registrationDate = registrationDate;
    this.lastLoginTime = lastLoginTime;
    this.lastLoginLocation = lastLoginLocation;
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

  public Integer getFlag() {
    return flag;
  }

  public void setFlag(Integer flag) {
    this.flag = flag;
  }

  public Long getRoleId() {
    return roleId;
  }

  public void setRoleId(Long roleId) {
    this.roleId = roleId;
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

}
