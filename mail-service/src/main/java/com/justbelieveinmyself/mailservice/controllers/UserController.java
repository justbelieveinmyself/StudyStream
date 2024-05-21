package com.justbelieveinmyself.mailservice.controllers;

import com.justbelieveinmyself.mailservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping
    public ResponseEntity<String> createUser(@RequestParam("userId") Long userId, @RequestParam("username") String username, @RequestParam("email") String email) {
        userService.saveUser(userId, username, email);
        return ResponseEntity.ok("Successfully created user");
    }
}
