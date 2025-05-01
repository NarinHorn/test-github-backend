package org.kps.appjwt.controller;

import lombok.RequiredArgsConstructor;
import org.kps.appjwt.jwt.JwtUtils;
import org.kps.appjwt.model.LoginRequest;
import org.kps.appjwt.model.response.ApiResponse;
import org.kps.appjwt.model.response.LoginDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class LoginController {

    private final AuthenticationManager authManager;
    private final JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginDto>> login(@RequestBody LoginRequest loginRequest) {

        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        try {
            // Authenticate username and password first before generate token (in case, username or password is not matched)
            Authentication auth = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );

            // generateToken in case username and password matched
            SecurityContextHolder.getContext().setAuthentication(auth);

            ApiResponse<LoginDto> response = ApiResponse.<LoginDto>builder()
                    .success(true)
                    .message("Login Successfully")
                    .status(HttpStatus.OK)
                    .payload(new LoginDto(jwtUtils.generateToken(email)))
                    .build();

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
