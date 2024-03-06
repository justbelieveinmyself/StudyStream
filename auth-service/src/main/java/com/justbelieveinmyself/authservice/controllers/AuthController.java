package com.justbelieveinmyself.authservice.controllers;

import com.justbelieveinmyself.authservice.domains.dtos.*;
import com.justbelieveinmyself.library.exception.InvalidRequestException;
import com.justbelieveinmyself.authservice.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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
    public ResponseEntity<UserDto> register(@RequestBody @Valid RegisterDto registerDto, BindingResult result) {
        validateErrors(result);
        UserDto userDto = authService.register(registerDto);
        return new ResponseEntity(userDto, HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<RefreshResponseDto> login(@RequestBody @Valid LoginRequestDto loginRequestDto, BindingResult result) {
        validateErrors(result);
        RefreshResponseDto responseDto = authService.login(loginRequestDto);
        return new ResponseEntity(responseDto, HttpStatus.OK);
    }

    @PostMapping("/refresh")
    public ResponseEntity<RefreshResponseDto> refresh(@RequestBody @Valid RefreshRequestDto refreshRequestDto, BindingResult result) {
        validateErrors(result);
        RefreshResponseDto responseDto = authService.refreshToken(refreshRequestDto);
        return new ResponseEntity(responseDto, HttpStatus.OK);
    }

    private void validateErrors(BindingResult result) {
        if(result.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            for (FieldError fieldError : result.getFieldErrors()) {
                errorMessage.append(fieldError.getDefaultMessage()).append("\n");
            }
            throw new InvalidRequestException(errorMessage.toString());
        }
    }
}
