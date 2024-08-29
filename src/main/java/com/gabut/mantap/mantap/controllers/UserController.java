package com.gabut.mantap.mantap.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;
import com.gabut.mantap.mantap.entities.User;
import com.gabut.mantap.mantap.helpers.ResponseFormatter;
import com.gabut.mantap.mantap.services.UserService;

@RequestMapping("/users")
@RestController
public class UserController {
  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/me")
  public ResponseEntity<?> authenticatedUser() {
    try {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

      User currentUser = (User) authentication.getPrincipal();

      return ResponseFormatter.success(currentUser, "User data fetched successfully", null, HttpStatus.OK.value(),
          HttpStatus.OK.value());
    } catch (Exception e) {
      return ResponseFormatter.error(null, "Error when fetching your data", e.getMessage(),
          HttpStatus.INTERNAL_SERVER_ERROR.value(),
          HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
  }

  @GetMapping("/")
  public ResponseEntity<Map<String, Object>> allUsers() {
    try {
      List<User> users = userService.allUsers();

      return ResponseFormatter.success(users, "Users data fetched successfully", null, HttpStatus.OK.value(),
          HttpStatus.OK.value());
    } catch (Exception e) {
      return ResponseFormatter.error(null, "Error when fetching users data", e.getMessage(),
          HttpStatus.INTERNAL_SERVER_ERROR.value(),
          HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
  }
}
