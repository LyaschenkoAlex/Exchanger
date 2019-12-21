package com.unicyb.security;

import com.unicyb.data.User;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class RegistrationForm {

  private String name;
  private String password;
  private String email;
  
  public User toUser(PasswordEncoder passwordEncoder) {
    return new User(name, passwordEncoder.encode(password), email);
  }
  
}
