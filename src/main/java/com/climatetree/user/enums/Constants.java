package com.climatetree.user.enums;

public enum Constants {
  SUCCESS  ("success"),
  USER("users"),
  EXCEPTION("exception"),
  ;

 private final String statusCode;
 Constants(String statusCode) {
    this.statusCode = statusCode;
  }

  public String getStatusCode() {
    return this.statusCode;
  }
}
