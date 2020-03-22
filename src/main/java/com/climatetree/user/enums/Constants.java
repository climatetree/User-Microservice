package com.climatetree.user.enums;

public enum Constants {
  SUCCESS  ("success"),
  USER("users"),
  EXCEPTION("exception"),
  ADMIN("1"),
  MODERATOR("2"),
  ADV_USERS("3"),
  LAST_LOGIN("US"),
  EMAIL("email"),
  USERNAME("username"),
  USERID("userId"),
  ROLE("role"),
  NICKNAME("nickName"),
  ISSUER("Climate Tree"),

  ;

 private final String statusCode;
 Constants(String statusCode) {
    this.statusCode = statusCode;
  }



  public String getStatusCode() {
    return this.statusCode;
  }

}
