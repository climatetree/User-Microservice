package com.climatetree.user.model;

import java.io.Serializable;

public class JwtRequest implements Serializable {

  private String username;
  private String email;
  private Integer roleId;

  public Integer getRoleId() {
    return roleId;
  }

  public void setRoleId(Integer roleId) {
    this.roleId = roleId;
  }



  //Important for JSON parsing
  public JwtRequest()
  {

  }

  public JwtRequest(String username, String email,Integer roleId) {
    this.setUsername(username);
    this.setEmail(email);
    this.setRoleId(roleId);
  }

  public String getUsername() {
    return this.username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

}

