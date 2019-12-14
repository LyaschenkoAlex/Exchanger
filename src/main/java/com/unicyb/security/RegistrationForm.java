package com.unicyb.security;

import com.unicyb.data.User;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
public class RegistrationForm {

  private String name;
  private String password;
  
  public User toUser(PasswordEncoder passwordEncoder) {
    return new User(name, passwordEncoder.encode(password));
  }
  
}
