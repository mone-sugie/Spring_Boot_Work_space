package com.college.yi.bookmanager.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGenerator {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String rawPassword1 = "admin123";
        String rawPassword2 = "user123";

        System.out.println("admin123 → " + encoder.encode(rawPassword1));
        System.out.println("user123  → " + encoder.encode(rawPassword2));
    }
}
