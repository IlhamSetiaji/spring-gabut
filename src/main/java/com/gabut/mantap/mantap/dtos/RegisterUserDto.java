package com.gabut.mantap.mantap.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RegisterUserDto {
  private String email;

  private String password;

  private String name;

  public RegisterUserDto(String email, String password, String name) {
    this.email = email;
    this.password = password;
    this.name = name;
  }
}
