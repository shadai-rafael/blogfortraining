package com.blogfortraining.restapi.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncoderPrinter {
    
    public static void main(String[] args) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println("Password: " +passwordEncoder.encode("password"));
        System.out.println("Admin: " +passwordEncoder.encode("admin"));
    }
}
