package com.cryptoapp.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cryptoapp.servicesimpl.OtpService;

@RestController
@RequestMapping("/api/otp")
public class OtpController {

    @Autowired
    private OtpService otpService;

    @PostMapping("/generate")
    public ResponseEntity<String> generateOtp(@RequestParam String email) {
        String otp = otpService.generateOtp(email);
        return ResponseEntity.ok("OTP sent to " + email);
    }

    @PostMapping("/verify")
    public ResponseEntity<String> verifyOtp(@RequestParam String email, @RequestParam String otp) {
        boolean isValid = otpService.verifyOtp(email, otp);
        if (isValid) {
            return ResponseEntity.ok("OTP verified successfully!");
        } else {
            return ResponseEntity.badRequest().body("Invalid OTP.");
        }
    }
}
