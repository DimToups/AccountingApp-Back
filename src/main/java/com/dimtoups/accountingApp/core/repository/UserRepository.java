package com.dimtoups.accountingApp.core.repository;

import com.dimtoups.accountingApp.core.entity.User;
import jakarta.validation.constraints.NotNull;
import org.hibernate.FetchNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

  default User update(@NotNull Long id, @NotNull User updatedUser) throws FetchNotFoundException {
    // Checking if the database user exists
    Optional<User> optionalDbUser = this.findById(id);
    if (optionalDbUser.isEmpty()) {
      throw new FetchNotFoundException(User.class.getName(), id);
    }
    User dbUser = optionalDbUser.get();

    // Overriding non-null values from the updatedUser into the dbUser
    PropertyMapper mapper = PropertyMapper.get();
    mapper.from(updatedUser::getFirstname)
        .to(dbUser::setFirstname);
    mapper.from(updatedUser::getLastname)
        .to(dbUser::setLastname);

    // Saving the modified object
    save(dbUser);

    return dbUser;
  }

  @Autowired
  Optional<User> findByUsername(String username);
}
