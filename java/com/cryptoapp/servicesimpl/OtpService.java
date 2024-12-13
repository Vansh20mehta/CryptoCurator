package com.cryptoapp.servicesimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;



import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class OtpService {

    private static final int OTP_LENGTH = 6;
    private final Random random = new SecureRandom();
    private final Map<String, String> otpStore = new HashMap<>(); // or use a database for persistence

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.properties.domain_name}")
    private String domainname;

    public String generateOtp(String email) {
        String otp = String.format("%06d", random.nextInt(1000000));
        otpStore.put(email, otp);
        sendOtpEmail(email, otp);
        return otp;
    }

    private void sendOtpEmail(String email, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Your OTP Code");
        message.setText("Your OTP code is: " + otp);
        message.setFrom(domainname);
         mailSender.send(message);
                
    }

    public boolean verifyOtp(String email, String otp) {
        String storedOtp = otpStore.get(email);
        if (storedOtp != null && storedOtp.equals(otp)) {
            otpStore.remove(email); // OTP can only be used once
            return true;
        }
        return false;
    }
}
