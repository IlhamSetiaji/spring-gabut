package com.gabut.mantap.mantap.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gabut.mantap.mantap.dtos.LoginUserDto;
import com.gabut.mantap.mantap.dtos.RegisterUserDto;
import com.gabut.mantap.mantap.entities.User;
import com.gabut.mantap.mantap.helpers.ResponseFormatter;
import com.gabut.mantap.mantap.responses.LoginResponse;
import com.gabut.mantap.mantap.services.AuthenticationService;
import com.gabut.mantap.mantap.services.JwtService;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {
  private final JwtService jwtService;

  private final AuthenticationService authenticationService;

  public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
    this.jwtService = jwtService;
    this.authenticationService = authenticationService;
  }

  @PostMapping("/signup")
  public ResponseEntity<?> register(@RequestBody RegisterUserDto registerUserDto) {
    try {
      User registeredUser = authenticationService.signup(registerUserDto);
      return ResponseFormatter.success(registeredUser, "User registered successfully", null, HttpStatus.CREATED.value(),
          HttpStatus.CREATED.value());
    } catch (Exception e) {
      return ResponseFormatter.error(null, "Failed to register user", e.getMessage(),
          HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
  }

  @PostMapping("/login")
  public ResponseEntity<?> authenticate(@RequestBody LoginUserDto loginUserDto) {
    try {
      User authenticatedUser = authenticationService.authenticate(loginUserDto);

      String jwtToken = jwtService.generateToken(authenticatedUser);

      LoginResponse loginResponse = new LoginResponse().setToken(jwtToken).setExpiresIn(jwtService.getExpirationTime());

      return ResponseFormatter.success(loginResponse, "User authenticated successfully", null, HttpStatus.OK.value(),
          HttpStatus.OK.value());
    } catch (Exception e) {
      return ResponseFormatter.error(null, "Failed to authenticate user", e.getMessage(),
          HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
  }
}
