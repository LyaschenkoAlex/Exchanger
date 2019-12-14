package com.unicyb.security;

import com.unicyb.data.User;
import com.unicyb.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserRepositoryUserDetailsService
        implements UserDetailsService {

  private UserRepository userRepo;

  @Autowired
  public UserRepositoryUserDetailsService(UserRepository userRepo) {
    this.userRepo = userRepo;
  }
  
  @Override
  public UserDetails loadUserByUsername(String name)
      throws UsernameNotFoundException {
    User user = userRepo.findByName(name);
    if (user != null) {
      return user;
    }
    throw new UsernameNotFoundException(
                    "User '" + name + "' not found");
  }

}
