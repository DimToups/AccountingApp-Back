package com.dimtoups.accountingApp.core.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity(name="users")
public class User {

  @Column(length = 50)
  private String firstname;

  @Column(length = 50)
  private String lastname;

  @Id
  @Column(length = 64, nullable = false, unique = true)
  private String username;

  @Column(length = 64, nullable = false)
  private String password;

  @Column(nullable = false)
  private boolean enabled;

  public User() {
  }

  public String getFirstname() {
    return this.firstname;
  }

  public String getLastname() {
    return this.lastname;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  public void setLastname(String lastname) {
    this.lastname = lastname;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
