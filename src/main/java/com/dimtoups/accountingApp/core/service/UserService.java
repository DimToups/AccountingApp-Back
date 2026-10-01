package com.dimtoups.accountingApp.core.service;

import com.dimtoups.accountingApp.core.api.dto.authentification.SignupRequestDto;
import com.dimtoups.accountingApp.core.entity.authority.Authority;
import com.dimtoups.accountingApp.core.entity.user.User;
import com.dimtoups.accountingApp.core.mapper.SignupRequestDbMapper;
import com.dimtoups.accountingApp.core.mapper.user.UserMapper;
import com.dimtoups.accountingApp.core.repository.AuthorizationRepository;
import com.dimtoups.accountingApp.core.repository.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import org.hibernate.FetchNotFoundException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

  private final PasswordEncoder passwordEncoder;


  //
  // Repositories
  //

  private final UserRepository userRepository;

  private final AuthorizationRepository authorizationRepository;


  //
  // Mappers
  //

  private final UserMapper userMapper;

  private final SignupRequestDbMapper signupRequestDbMapper;


  public UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder, SignupRequestDbMapper signupRequestDbMapper, AuthorizationRepository authorizationRepository) {
    this.userRepository = userRepository;
    this.userMapper = userMapper;
    this.passwordEncoder = passwordEncoder;
    this.signupRequestDbMapper = signupRequestDbMapper;
    this.authorizationRepository = authorizationRepository;
  }

  public User updateUser(@NotNull User updatedUser) throws FetchNotFoundException {
    // Checking if the database user exists
    Optional<User> optionalDbUser = userRepository.findByUsername(updatedUser.getUsername());
    if (optionalDbUser.isEmpty()) {
      throw new FetchNotFoundException(User.class.getName(), updatedUser.getUsername());
    }
    User dbUser = optionalDbUser.get();

    // Overriding non-null values from the updatedUser into the dbUser
    dbUser = userMapper.updateUser(dbUser, updatedUser);

    // Updating the user in the database
    return userRepository.save(dbUser);
  }

  @Transactional
  public void deleteUser(String username) throws FetchNotFoundException {
    // Checking if the user exists
    Optional<User> optionalUser = userRepository.findByUsername(username);
    if (optionalUser.isEmpty()) {
      throw new FetchNotFoundException(User.class.getName(), username);
    }

    // Removing every constraint from the user
    authorizationRepository.deleteAllByUser(optionalUser.get());

    userRepository.deleteByUsername(username);
  }

  public Optional<User> findByUsername(String username) {
    return userRepository.findByUsername(username);
  }

  public void signup(SignupRequestDto signupRequestDto) throws DuplicateKeyException {
    // Checking if the username does not already exist
    if (userRepository.findByUsername(signupRequestDto.username()).isPresent()) {
      throw new DuplicateKeyException("A user with the username " + signupRequestDto.username() + " already exists");
    }

    // Creating the database user
    User newUser = signupRequestDbMapper.signupRequestToUser(signupRequestDto);
    newUser.setPassword(passwordEncoder.encode(signupRequestDto.password()));
    newUser.setEnabled(true);
    userRepository.save(newUser);

    // Adding the default role to the user
    Authority authority = new Authority(newUser, Authority.Authorities.ROLE_USER);
    authorizationRepository.save(authority);
  }
}
