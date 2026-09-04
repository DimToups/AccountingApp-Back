package com.dimtoups.accountingApp.core.service;

import com.dimtoups.accountingApp.core.entity.User;
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

  public User updateUser(@NotNull String username, @NotNull User updatedUser) throws FetchNotFoundException {
    // Checking if the user exists
    if (userRepository.findByUsername(username).isEmpty()) {
      throw new FetchNotFoundException(User.class.getName(), username);
    }

    // Updating the user in the database
    return userRepository.save(updatedUser);
  }

  public Optional<User> findById(Long id) {
    return userRepository.findById(id);
  }

  public User createUser(User user) {
    return userRepository.save(user);
  }

  public void replaceUser(User user) throws FetchNotFoundException {
    // Checking if the user exists
    if (userRepository.findByUsername(user.getUsername()).isEmpty()) {
      throw new FetchNotFoundException(User.class.getName(), user.getUsername());
    }

    userRepository.save(user);
  }

  public void deleteUser(String username) throws FetchNotFoundException {
    // Checking if the user exists
    Optional<User> optionalUser = userRepository.findByUsername(username);
    if (optionalUser.isEmpty()) {
      throw new FetchNotFoundException(User.class.getName(), username);
    }

    userRepository.delete(optionalUser.get());
  }

  public Optional<User> findByUsername(String username) {
    return userRepository.findByUsername(username);
  }
}
