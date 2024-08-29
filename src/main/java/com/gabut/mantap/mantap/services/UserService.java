package com.gabut.mantap.mantap.services;

import java.util.List;
import java.util.ArrayList;
import org.springframework.stereotype.Service;

import com.gabut.mantap.mantap.entities.User;
import com.gabut.mantap.mantap.repositories.UserRepository;

@Service
public class UserService {
  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public List<User> allUsers() {
    List<User> users = new ArrayList<>();

    userRepository.findAll().forEach(users::add);

    return users;
  }
}
