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

    @Operation(
            summary = "Verify OTP"
    )
    @PostMapping("/verify")
    public ResponseEntity<?> verifyOtp(@RequestParam String email, @RequestParam String otp) {
        return ResponseEntity.ok(appUserService.verifyOtp(email, otp));
    }

    @Operation(
            summary = "Verify OTP Reset Password"
    )
    @PostMapping("/verify-resetPassword")
    public ResponseEntity<?> verifyOtpResetPassword(@RequestParam String email, @RequestParam String otp) {
        return ResponseEntity.ok(appUserService.verifyOtp(email, otp));
    }

    @Operation(
            summary = "Resend OTP"
    )
    @PostMapping("/resendOtp")
    public ResponseEntity<?> resendOtp(@RequestParam String email) {
        return ResponseEntity.ok(appUserService.resendOtp(email));
    }
}
