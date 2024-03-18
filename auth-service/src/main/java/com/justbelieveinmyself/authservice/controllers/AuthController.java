package com.justbelieveinmyself.authservice.controllers;

import com.justbelieveinmyself.authservice.domains.dtos.*;
import com.justbelieveinmyself.authservice.services.RefreshTokenService;
import com.justbelieveinmyself.authservice.domains.dtos.UserDto;
import com.justbelieveinmyself.library.aspects.ValidateErrors;
import com.justbelieveinmyself.authservice.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/register")
    @ValidateErrors
    public ResponseEntity<UserDto> register(@RequestBody @Valid RegisterDto registerDto, BindingResult result) {
        UserDto userDto = authService.register(registerDto);
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    @ValidateErrors
    public ResponseEntity<RefreshResponseDto> login(@RequestBody @Valid LoginRequestDto loginRequestDto, BindingResult result) {
        RefreshResponseDto responseDto = authService.login(loginRequestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PostMapping("/refresh")
    @ValidateErrors
    public ResponseEntity<RefreshResponseDto> refresh(@RequestBody @Valid RefreshRequestDto refreshRequestDto, BindingResult result) {
        RefreshResponseDto responseDto = refreshTokenService.refreshToken(refreshRequestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

}
