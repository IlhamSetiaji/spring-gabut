package com.gabut.mantap.mantap.services;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gabut.mantap.mantap.dtos.RegisterUserDto;
import com.gabut.mantap.mantap.entities.Role;
import com.gabut.mantap.mantap.entities.User;
import com.gabut.mantap.mantap.entities.enums.RoleEnum;
import com.gabut.mantap.mantap.repositories.RoleRepository;
import com.gabut.mantap.mantap.repositories.UserRepository;

@Service
public class UserService {
  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final PasswordEncoder passwordEncoder;

  public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public List<User> allUsers() {
    List<User> users = new ArrayList<>();

    userRepository.findAll().forEach(users::add);

    return users;
  }

  public User createAdministrator(RegisterUserDto input) {
    Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.ADMIN);

    if (optionalRole.isEmpty()) {
      return null;
    }

    User user = new User()
        .setName(input.getName())
        .setEmail(input.getEmail())
        .setPassword(passwordEncoder.encode(input.getPassword()))
        .setRole(optionalRole.get());

    return userRepository.save(user);
  }
}
