package com.dimtoups.accountingApp.core.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity(name="users")
public class User {
  @Id
  @GeneratedValue
  private long id;

  @Column(nullable = false)
  private String firstname;

  @Column()
  private String lastname;

  protected User() {
  }

  public User(String firstname) {
    this.firstname = firstname;
  }

  public User(String firstname, String lastname) {
    this.firstname = firstname;
    this.lastname = lastname;
  }

  public User(Long id, String firstname, String lastname) {
    this.id = id;
    this.firstname = firstname;
    this.lastname = lastname;
  }

  public long getId() {
    return this.id;
  }

  public String getFirstname() {
    return this.firstname;
  }

  public String getLastname() {
    return this.lastname;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  public void setLastname(String lastname) {
    this.lastname = lastname;
  }
}
