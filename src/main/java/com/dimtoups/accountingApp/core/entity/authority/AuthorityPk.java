package com.dimtoups.accountingApp.core.entity.authority;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class AuthorityPk {

  @Column(name = "username")
  private String username;

  @Column
  private String authority;

  public AuthorityPk() {

  }

  public AuthorityPk(String username, Authority.Authorities authorities) {
    this.username = username;
    this.authority = authorities.name();
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
