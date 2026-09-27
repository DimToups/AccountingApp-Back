package com.dimtoups.accountingApp.core.api.controller;

import com.dimtoups.accountingApp.core.api.dto.authentification.LoginRequestDto;
import com.dimtoups.accountingApp.core.api.dto.authentification.LoginResponseDto;
import com.dimtoups.accountingApp.core.api.dto.authentification.SignupRequestDto;
import com.dimtoups.accountingApp.core.api.helper.JwtHelper;
import com.dimtoups.accountingApp.core.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthentificationController {

  private final AuthenticationManager authenticationManager;

  private final UserService userService;

  public AuthentificationController(AuthenticationManager authenticationManager, UserService userService) {
    this.authenticationManager = authenticationManager;
    this.userService = userService;
  }


  //
  // Requests
  //

  @PostMapping("/signup")
  public ResponseEntity<String> signup(@Valid @RequestBody SignupRequestDto signupRequestDto) {
    userService.signup(signupRequestDto);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @PostMapping("/login")
  public ResponseEntity<LoginResponseDto> login(
      @Valid @RequestBody LoginRequestDto loginRequestDto,
      HttpServletRequest request,
      HttpServletResponse response,
      CookieCsrfTokenRepository csrfTokenRepository) {
    // Authenticating the user
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginRequestDto.username(), loginRequestDto.password()));

    // Generating the user's JWT and csrf token
    String jwToken = JwtHelper.generateToken(loginRequestDto.username());
    CsrfToken csrfToken = csrfTokenRepository.generateToken(request);
    csrfTokenRepository.saveToken(csrfToken, request, response);

    return ResponseEntity.ok(new LoginResponseDto(jwToken, csrfToken));
  }
}
