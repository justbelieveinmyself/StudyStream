package com.justbelieveinmyself.authservice.controllers;

import com.justbelieveinmyself.authservice.domains.dtos.*;
import com.justbelieveinmyself.authservice.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<RefreshResponseDto> login(@RequestBody @Valid LoginRequestDto loginRequestDto) {
        RefreshResponseDto responseDto = authService.login(loginRequestDto);
        return new ResponseEntity(responseDto, HttpStatus.OK);
    }

    @PostMapping("/refresh")
    public ResponseEntity<RefreshResponseDto> refresh(@RequestBody @Valid RefreshRequestDto refreshRequestDto) {
        RefreshResponseDto responseDto = authService.refreshToken(refreshRequestDto);
        return new ResponseEntity(responseDto, HttpStatus.OK);
    }
}
