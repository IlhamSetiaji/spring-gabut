package com.gabut.mantap.mantap.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginUserDto {
  private String email;
  private String password;

  public LoginUserDto(String email, String password) {
    this.email = email;
    this.password = password;
  }
}
