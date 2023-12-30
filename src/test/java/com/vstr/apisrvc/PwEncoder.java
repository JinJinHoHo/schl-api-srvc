package com.vstr.apisrvc;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PwEncoder {
    public static void main(String[] args) {
        PasswordEncoder passwordEncoder =  new BCryptPasswordEncoder();
        System.out.println(passwordEncoder.encode("1234"));
    }
}
