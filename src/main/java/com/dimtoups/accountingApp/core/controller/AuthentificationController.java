package com.dimtoups.accountingApp.core.controller;

import com.dimtoups.accountingApp.core.controller.dto.authentification.*;
import com.dimtoups.accountingApp.core.helper.JwtHelper;
import com.dimtoups.accountingApp.core.mapper.user.UserControllerDtoToServiceDtoMapper;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthentificationController {

  private final AuthenticationManager authenticationManager;

  private final UserService userService;

  private final UserControllerDtoToServiceDtoMapper userControllerDtoToServiceDtoMapper;

  public AuthentificationController(AuthenticationManager authenticationManager, UserService userService, UserControllerDtoToServiceDtoMapper userControllerDtoToServiceDtoMapper) {
    this.authenticationManager = authenticationManager;
    this.userService = userService;
    this.userControllerDtoToServiceDtoMapper = userControllerDtoToServiceDtoMapper;
  }


  //
  // Requests
  //

  @GetMapping("/signup")
  public ResponseEntity<GetSignupResponseDto> signup(
      CookieCsrfTokenRepository csrfTokenRepository,
      HttpServletRequest request,
      HttpServletResponse response) {
    // Generating a csrf token
    CsrfToken csrfToken = csrfTokenRepository.generateToken(request);
    csrfTokenRepository.saveToken(csrfToken, request, response);

    GetSignupResponseDto getSignupResponseDto = new GetSignupResponseDto(csrfToken);
    return ResponseEntity.status(200).body(getSignupResponseDto);
  }

  @PostMapping("/signup")
  public ResponseEntity<String> signup(@Valid @RequestBody PostSignupRequestDto postSignupRequestDto) {
    userService.createUser(userControllerDtoToServiceDtoMapper.createUser(postSignupRequestDto));
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @GetMapping("/login")
  public ResponseEntity<GetLoginResponseDto> login(
      CookieCsrfTokenRepository csrfTokenRepository,
      HttpServletRequest request,
      HttpServletResponse response) {
    // Generating a csrf token
    CsrfToken csrfToken = csrfTokenRepository.generateToken(request);
    csrfTokenRepository.saveToken(csrfToken, request, response);

    GetLoginResponseDto getLoginResponseDto = new GetLoginResponseDto(csrfToken);
    return ResponseEntity.status(200).body(getLoginResponseDto);
  }

  @PostMapping("/login")
  public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody PostLoginRequestDto loginRequestDto) {
    // Authenticating the user
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginRequestDto.username(), loginRequestDto.password()));

    // Generating the user's JWT
    String jwToken = JwtHelper.generateToken(loginRequestDto.username());

    return ResponseEntity.ok(new LoginResponseDto(jwToken));
  }
}
