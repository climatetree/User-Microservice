package com.climatetree.user.model;

import java.io.Serializable;

public class JwtRequest implements Serializable {

  private String username;
  private String email;

  //Important for JSON parsing
  public JwtRequest()
  {

  }

  public JwtRequest(String username, String email) {
    this.setUsername(username);
    this.setEmail(email);
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

