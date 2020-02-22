package com.CS6510.microservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

public class User {

  private Long userId;
  private String email;
  private String nickname;
  private Integer flag;
  private Long roleId;
  private Date createTime;
  private Date lastLoginTime;
  private String lastLoginLocation;


}
