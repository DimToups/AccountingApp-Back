package com.dimtoups.accountingApp.core.entity.authority;

import com.dimtoups.accountingApp.core.entity.user.User;
import jakarta.persistence.*;

@Entity(name = "authorities")
public class Authority {

  @EmbeddedId
  private AuthorityPk authorityPk;

  @ManyToOne
  @MapsId(value = "username")
  @JoinColumn(name = "username", referencedColumnName = "username")
  private User user;

  public AuthorityPk getAuthorityPk() {
    return authorityPk;
  }

  public Authority() {

  }

  public Authority(Authorities authority, User user) {
    this.authorityPk = new AuthorityPk(user.getUsername(), authority);
    this.user = user;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  /**
   * The list of possible authorities in the application
   */
  public enum Authorities {
    ROLE_ADMIN,
    ROLE_USER
  }
}
