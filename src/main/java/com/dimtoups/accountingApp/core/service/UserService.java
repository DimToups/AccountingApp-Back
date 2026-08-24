package com.dimtoups.accountingApp.core.service;

import com.dimtoups.accountingApp.core.entity.User;
import com.dimtoups.accountingApp.core.repository.UserRepository;
import jakarta.validation.constraints.NotNull;
import org.hibernate.FetchNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  public User updateUser(@NotNull Long id, @NotNull User updatedUser) throws FetchNotFoundException {
    // Checking if the user exists
    if (userRepository.existsById(id)) {
      throw new FetchNotFoundException(User.class.getName(), id);
    }

    // Updating the user in the database
    return userRepository.update(id, updatedUser);
  }

  public Collection<User> findAllUsers() {
    Collection<User> users = new ArrayList<>();
    userRepository.findAll().forEach(users::add);
    return users;
  }

  public Optional<User> findById(Long id) {
    return userRepository.findById(id);
  }

  public void createUser(User user) {
    userRepository.save(user);
  }

  public void replaceUser(User user) throws FetchNotFoundException {
    // Checking if the user exists
    if (userRepository.existsById(user.getId())) {
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
