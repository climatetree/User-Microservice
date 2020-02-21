package com.CS6510.microservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection="User")
public class User {

  @Id
  private String email;
  private String first_name;
  private String last_name;
  private Date registration_date;


  public User(){
    this.registration_date = new Date();
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getFirst_name() {
    return first_name;
  }

  public void setFirst_name(String first_name) {
    this.first_name = first_name;
  }

  public String getLast_name() {
    return last_name;
  }

  public void setLast_name(String last_name) {
    this.last_name = last_name;
  }

  public Date getRegistration_date() {
    return registration_date;
  }

  public void setRegistration_date(Date registration_date) {
    this.registration_date = new Date();
  }
}
