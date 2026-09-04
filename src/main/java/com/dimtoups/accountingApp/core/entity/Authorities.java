package com.dimtoups.accountingApp.core.entity;

import jakarta.persistence.*;

@Entity(name = "authorities")
public class Authorities {
  @Id
  @GeneratedValue
  private long id;

  @ManyToOne(targetEntity = User.class)
  private String username;

  @Column(length = 128)
  private String authority;

  public long getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getAuthority() {
    return authority;
  }

  public void setAuthority(String authority) {
    this.authority = authority;
  }
}
