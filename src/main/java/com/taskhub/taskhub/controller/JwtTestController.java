package com.taskhub.taskhub.controller;

import com.taskhub.taskhub.security.JwtService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/test")
public class JwtTestController {

    private final JwtService jwtService;

    public JwtTestController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @GetMapping("/generate")
    public String generate() {
        UserDetails userDetails = User.builder()
                .username("john@example.com")
                .password("x")
                .roles("USER")
                .build();
        return jwtService.generateToken(userDetails, 1L, "USER");
    }


}