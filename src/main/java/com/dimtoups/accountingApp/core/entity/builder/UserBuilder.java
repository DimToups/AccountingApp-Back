package com.dimtoups.accountingApp.core.entity.builder;

import com.dimtoups.accountingApp.core.entity.User;

public class UserBuilder {
  private final User user;

  public UserBuilder() {
    this.user = new User();
  }

  public UserBuilder setFirstname(String firstName) {
    this.user.setFirstname(firstName);
    return this;
  }

  public UserBuilder setLastname(String lastname) {
    this.user.setLastname(lastname);
    return this;
  }

  public UserBuilder setUsername(String username) {
    this.user.setUsername(username);
    return this;
  }

  public UserBuilder setPassword(String password) {
    this.user.setPassword(password);
    return this;
  }

  public User build() {
    return user;
  }
}
