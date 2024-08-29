package com.gabut.mantap.mantap.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gabut.mantap.mantap.dtos.LoginUserDto;
import com.gabut.mantap.mantap.dtos.RegisterUserDto;
import com.gabut.mantap.mantap.entities.User;
import com.gabut.mantap.mantap.repositories.UserRepository;

@Service
public class AuthenticationService {
  private final UserRepository userRepository;

  private final PasswordEncoder passwordEncoder;

  private final AuthenticationManager authenticationManager;

  public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder,
      AuthenticationManager authenticationManager) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.authenticationManager = authenticationManager;
  }

  public User signup(RegisterUserDto input) {
    User user = new User()
        .setName(input.getName())
        .setEmail(input.getEmail())
        .setPassword(passwordEncoder.encode(input.getPassword()));

    return userRepository.save(user);
  }

  public User authenticate(LoginUserDto input) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            input.getEmail(),
            input.getPassword()));

    return userRepository.findByEmail(input.getEmail())
        .orElseThrow();
  }
}
