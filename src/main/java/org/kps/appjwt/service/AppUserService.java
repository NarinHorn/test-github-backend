package org.kps.appjwt.service;


import org.kps.appjwt.model.AppUser;
import org.kps.appjwt.model.AppUserDto;
import org.kps.appjwt.model.AppUserRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface AppUserService extends UserDetailsService {
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    AppUserDto registerUser(AppUserRequest appUserRequest);
}
