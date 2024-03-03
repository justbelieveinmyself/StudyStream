package com.justbelieveinmyself.authservice.controllers;

import com.justbelieveinmyself.authservice.domains.dtos.LoginRequestDto;
import com.justbelieveinmyself.authservice.domains.dtos.LoginResponseDto;
import com.justbelieveinmyself.authservice.domains.dtos.RegisterDto;
import com.justbelieveinmyself.authservice.domains.dtos.UserDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.justbelieveinmyself.authservice.services.AuthService;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody @Valid RegisterDto registerDto) {
         UserDto userDto = authService.register(registerDto);
         return new ResponseEntity(userDto, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequestDto loginRequestDto) {
        LoginResponseDto responseDto = authService.login(loginRequestDto);
        return new ResponseEntity(responseDto, HttpStatus.OK);
    }

}
