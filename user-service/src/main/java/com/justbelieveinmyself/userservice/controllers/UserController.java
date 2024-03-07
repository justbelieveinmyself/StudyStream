package com.justbelieveinmyself.userservice.controllers;

import com.justbelieveinmyself.library.dto.ResponseMessage;
import com.justbelieveinmyself.library.exception.InvalidRequestException;
import com.justbelieveinmyself.userservice.domains.dtos.UpdateUserDto;
import com.justbelieveinmyself.userservice.domains.dtos.UserDto;
import com.justbelieveinmyself.userservice.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.NotImplementedException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long userId) {
        return userService.getUserById(userId);
    }

    @GetMapping
    public ResponseEntity<UserDto> getCurrentUser(@RequestHeader("X-Username") String username) {
        return userService.getUserByUsername(username);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUserById(
            @PathVariable Long userId,
            @RequestBody @Valid UpdateUserDto dto,
            BindingResult result
    ) {
        validateErrors(result);
        ResponseEntity<UserDto> response = userService.updateUserById(userId, dto);
        return response;
    }

    @PutMapping
    public ResponseEntity<UserDto> updateCurrentUser(
            @RequestHeader("X-Username") String username,
            @RequestBody @Valid UpdateUserDto dto,
            BindingResult result
    ) {
        validateErrors(result);
        return userService.updateUserByUsername(username, dto);
    }

    @DeleteMapping
    public ResponseEntity<ResponseMessage> deleteCurrentUser(
            @RequestHeader("X-Username") String username
    ) {
        return userService.deleteUserByUsername(username);
    }

    private void validateErrors(BindingResult result) { //TODO: to bean or library
        if(result.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            for (FieldError fieldError : result.getFieldErrors()) {
                errorMessage.append(fieldError.getDefaultMessage()).append("\n");
            }
            throw new InvalidRequestException(errorMessage.toString());
        }
    }

}

// TODO: swagger api
// @RequestHeader("X-Username") String username, @RequestHeader("X-User-Roles") String[] roles