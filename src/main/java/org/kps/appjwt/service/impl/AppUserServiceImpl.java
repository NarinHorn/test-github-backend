package org.kps.appjwt.service.impl;

import lombok.RequiredArgsConstructor;
import org.kps.appjwt.model.AppUser;
import org.kps.appjwt.model.AppUserDto;
import org.kps.appjwt.model.AppUserRequest;
import org.kps.appjwt.repository.AppUserRepository;
import org.kps.appjwt.service.AppUserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppUserServiceImpl implements AppUserService {

    private final ModelMapper modelMapper = new ModelMapper();
    private final PasswordEncoder passwordEncoder;
    private final AppUserRepository appUserRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return appUserRepository.findUserByEmail(email);
    }

    @Override
    public AppUserDto registerUser(AppUserRequest appUserRequest) {

        String encodedPassword = passwordEncoder.encode(appUserRequest.getPassword());

        appUserRequest.setPassword(encodedPassword);

        AppUser appUser = appUserRepository.insertUser(appUserRequest);

        Integer userId = appUser.getId();

        for(Integer roleId : appUserRequest.getRoles()) {
            appUserRepository.insertIntoUserRole(userId, roleId);
        }

        return modelMapper.map(appUserRepository.findUserByUserId(userId), AppUserDto.class);
    }
}
