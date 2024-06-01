package com.justbelieveinmyself.courseservice.controllers;

import com.justbelieveinmyself.courseservice.services.UserService;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Hidden
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody Long userId) {
        userService.saveUser(userId);
        return ResponseEntity.ok("Successfully created user");
    }

}
