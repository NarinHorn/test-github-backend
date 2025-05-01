package org.kps.appjwt.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.kps.appjwt.model.AppUserRequest;
import org.kps.appjwt.service.AppUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AppUserController {

    private final AppUserService appUserService;

    @Operation(
            summary = "Register User"
    )
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody AppUserRequest appUserRequest) {
        return ResponseEntity.ok(appUserService.registerUser(appUserRequest));
    }
}
