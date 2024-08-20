package com.management.farm.Util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderRunner {
    public static void main(String[] args) {
        // Create a new BCryptPasswordEncoder instance
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        
        // Password to encode
        String rawPassword = "qwerty";
        
        // Encode the password
        String encodedPassword = encoder.encode(rawPassword);
        
        // Print the encoded password
        System.out.println("Encoded password: " + encodedPassword);
    }
}
