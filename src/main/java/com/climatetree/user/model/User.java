package com.climatetree.user.model;


import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/*
Object representation of the USER table from the database. Setters and getters + constructors.
 */
@Entity
@Table(name = "user", schema = "public")
public class User {
  @Id
  @Column(name = "user_id", nullable = false)
  private Long userId;

  @Column(name = "email")
  private String email;

  @Column(name = "nickname")
  private String nickname;

  @Column(name = "flag")
  private Boolean flag;

  @Column(name = "role_id")
  private Integer roleId;

  @Column(name = "creation_date")
  private Date registrationDate;

  @Column(name = "last_login_date")
  private Date lastLoginTime;

  @Column(name = "last_login_location")
  private String lastLoginLocation;



  public User() {
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

  public Integer getRoleId() {
    return roleId;
  }

  public void setRoleId(Integer roleId) {
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

  @Override
  public String toString() {
    return "User{" +
            "userId=" + userId +
            ", email='" + email + '\'' +
            ", nickname='" + nickname + '\'' +
            ", flag=" + flag +
            ", roleId=" + roleId +
            ", registrationDate=" + registrationDate +
            ", lastLoginTime=" + lastLoginTime +
            ", lastLoginLocation='" + lastLoginLocation + '\'' +
            '}';
  }
}