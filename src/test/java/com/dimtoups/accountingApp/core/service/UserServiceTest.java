package com.dimtoups.accountingApp.core.service;

import com.dimtoups.accountingApp.core.entity.authority.Authority;
import com.dimtoups.accountingApp.core.entity.user.User;
import com.dimtoups.accountingApp.core.repository.AuthorizationRepository;
import com.dimtoups.accountingApp.core.service.dto.users.DeleteUserDto;
import com.dimtoups.accountingApp.core.service.dto.users.UpdateUserDto;
import org.hibernate.FetchNotFoundException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@SqlGroup({
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sampleDatabases/CreateUserServiceSampleDB.sql"),
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:sampleDatabases/DeleteUserServiceSampleDB.sql")
})
public class UserServiceTest {
  /**
   * A generic username present in the CreateUserServiceSampleDB.sql script.
   * A {@link User} should be found in the test database with this username.
   */
  public static final String BASIC_USERNAME = "Pizza";

  /**
   * A username not present in the database.
   * This username should be used for tests with unknown usernames.
   */
  public static final String UNKNOWN_USERNAME = "Pineapple";

  @Autowired
  private UserService userService;

  @Autowired
  private AuthorizationRepository authorizationRepository;

  @Nested
  public class FindByUsername {
    @Test
    public void isUserFoundGivenACorrectUsername() {
      // Given
      String username = BASIC_USERNAME;

      // When
      Optional<User> optionalUser = userService.findByUsername(username);

      // Then
      assertTrue(optionalUser.isPresent() && optionalUser.get().username.equals(username));
    }

    @Test
    public void areNoUserFoundWithAnUnknownUsername() {
      // Given
      String username = UNKNOWN_USERNAME;

      // When
      Optional<User> optionalUser = userService.findByUsername(username);

      // Then
      assertTrue(optionalUser.isEmpty());
    }
  }

  @Nested
  public class UpdateUser {

    @Test
    public void isReturnedUserUpdated() {
      // Given
      User originalUserCopy = userService.findByUsername(BASIC_USERNAME).get();
      UpdateUserDto updatedUser = new UpdateUserDto(BASIC_USERNAME, "Ananas", null);

      // When
      User updatedDbUser = userService.updateUser(updatedUser);

      // Then
      assertNotEquals(originalUserCopy, updatedDbUser);
    }

    @Test
    public void isDatabaseUserUpdated() {
      // Given
      User originalUserCopy = userService.findByUsername(BASIC_USERNAME).get();
      UpdateUserDto updatedUser = new UpdateUserDto(BASIC_USERNAME, null, "Ananas");

      // When
      userService.updateUser(updatedUser);

      // Then
      assertNotEquals(originalUserCopy, userService.findByUsername(BASIC_USERNAME).get());
    }

    @Test
    public void areUnupdatedVariablesUnchanged() {
      // Given
      User originalUserCopy = userService.findByUsername(BASIC_USERNAME).get();
      UpdateUserDto updatedUser = new UpdateUserDto(BASIC_USERNAME, null, "Ananas");

      // When
      User updatedDbUser = userService.updateUser(updatedUser);

      // Then
      assertEquals(originalUserCopy.getFirstname(), updatedDbUser.getFirstname());
    }

    @Test
    public void isDatabaseUserEnabledVariableIgnored() {
      // Given
      UpdateUserDto updatedUser = new UpdateUserDto(BASIC_USERNAME, null, null);

      // When
      userService.updateUser(updatedUser);

      // Then
      assertTrue(userService.findByUsername(BASIC_USERNAME).get().isEnabled());
    }

    @Test
    public void isAnExceptionThrownWhenGivenAnIncorrectUsername() {
      // Given
      UpdateUserDto unknownUser = new UpdateUserDto(UNKNOWN_USERNAME, null, null);

      // When / Then
      assertThrowsExactly(FetchNotFoundException.class,
          () -> userService.updateUser(unknownUser));
    }
  }

  @Nested
  public class DeleteUser {
    @Test
    public void isTheUserDeletedWhenGivenItsUsername() {
      // Given
      DeleteUserDto deleteUserDto = new DeleteUserDto(BASIC_USERNAME);
      Optional<User> originalUser = userService.findByUsername(deleteUserDto.username());

      // When
      userService.deleteUser(deleteUserDto);
      Optional<User> optionalUser = userService.findByUsername(deleteUserDto.username());

      // Then
      assertTrue(originalUser.isPresent());
      assertTrue(optionalUser.isEmpty());
    }

    @Test
    public void areAuthoritiesDeletedWithTheUser() {
      // Given
      User originalUser = userService.findByUsername(BASIC_USERNAME).get();
      List<Authority> originalAuthorities = authorizationRepository.findAllByUser(originalUser);
      DeleteUserDto deleteUserDto = new DeleteUserDto(originalUser.getUsername());

      // When
      userService.deleteUser(deleteUserDto);
      List<Authority> newAuthorities = authorizationRepository.findAllByUser(originalUser);

      // Then
      assertFalse(originalAuthorities.isEmpty());
      assertTrue(newAuthorities.isEmpty());
    }

    @Test
    public void isNoUserDeletedWhenGivenAnIncorrectUsername() {
      // Given
      DeleteUserDto deleteUserDto = new DeleteUserDto(UNKNOWN_USERNAME);

      // When / Then
      assertThrowsExactly(
          FetchNotFoundException.class,
          () -> userService.deleteUser(deleteUserDto));
    }
  }
}
