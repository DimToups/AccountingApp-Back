package com.dimtoups.accountingApp.core.service;

import com.dimtoups.accountingApp.core.entity.User;
import com.dimtoups.accountingApp.core.mapper.UserMapper;
import com.dimtoups.accountingApp.core.repository.UserRepository;
import jakarta.validation.constraints.NotNull;
import org.hibernate.FetchNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private UserMapper userMapper;

  public User updateUser(@NotNull Long id, @NotNull User updatedUser) throws FetchNotFoundException {
    // Checking if the database user exists
    Optional<User> optionalDbUser = this.findById(id);
    if (optionalDbUser.isEmpty()) {
      throw new FetchNotFoundException(User.class.getName(), id);
    }
    User dbUser = optionalDbUser.get();

    // Overriding non-null values from the updatedUser into the dbUser
    dbUser = userMapper.updateUser(dbUser, updatedUser);

    // Updating the user in the database
    return userRepository.save(dbUser);
  }

  public Optional<User> findById(Long id) {
    return userRepository.findById(id);
  }

  public void createUser(User user) {
    userRepository.save(user);
  }

  public void replaceUser(User user) throws FetchNotFoundException {
    // Checking if the user exists
    if (!userRepository.existsById(user.getId())) {
      throw new FetchNotFoundException(User.class.getName(), user.getId());
    }

    userRepository.save(user);
  }

  public void deleteUser(Long id) throws FetchNotFoundException {
    // Checking if the user exists
    if (!userRepository.existsById(id)) {
      throw new FetchNotFoundException(User.class.getName(), id);
    }

    userRepository.deleteById(id);
  }
}
