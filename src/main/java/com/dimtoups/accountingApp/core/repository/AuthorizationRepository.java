package com.dimtoups.accountingApp.core.repository;

import com.dimtoups.accountingApp.core.entity.authority.Authority;
import com.dimtoups.accountingApp.core.entity.authority.AuthorityPk;
import com.dimtoups.accountingApp.core.entity.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorizationRepository extends CrudRepository<Authority, AuthorityPk> {

  @Autowired
  void deleteAllByUser(User username);
}
