package com.gabut.mantap.mantap.services;

import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gabut.mantap.mantap.dtos.LoginUserDto;
import com.gabut.mantap.mantap.dtos.RegisterUserDto;
import com.gabut.mantap.mantap.entities.Role;
import com.gabut.mantap.mantap.entities.User;
import com.gabut.mantap.mantap.entities.enums.RoleEnum;
import com.gabut.mantap.mantap.repositories.RoleRepository;
import com.gabut.mantap.mantap.repositories.UserRepository;

@Service
public class AuthenticationService {
  private final UserRepository userRepository;

  private final PasswordEncoder passwordEncoder;

  private final AuthenticationManager authenticationManager;

  private final RoleRepository roleRepository;

  public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder,
      AuthenticationManager authenticationManager, RoleRepository roleRepository) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.authenticationManager = authenticationManager;
    this.roleRepository = roleRepository;
  }

  public User signup(RegisterUserDto input) {
    Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.USER);

    if (optionalRole.isEmpty()) {
      throw new RuntimeException("Role not found");
    }
    User user = new User().setRole(optionalRole.get())
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
