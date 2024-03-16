package com.justbelieveinmyself.userservice.controllers;

import com.justbelieveinmyself.library.dto.ResponseMessage;
import com.justbelieveinmyself.library.exception.InvalidRequestException;
import com.justbelieveinmyself.userservice.domains.dtos.UpdateUserDto;
import com.justbelieveinmyself.userservice.domains.dtos.UserDto;
import com.justbelieveinmyself.userservice.services.UserService;
import jakarta.validation.Valid;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
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
        UserDto responseDto = userService.getUserById(userId);
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUserById(
            @PathVariable Long userId,
            @RequestBody @Valid UpdateUserDto requestDto,
            BindingResult result
    ) {
        validateErrors(result);
        UserDto responseDto = userService.updateUserById(userId, requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping
    public ResponseEntity<UserDto> getCurrentUser(@RequestHeader("X-Username") String username) {
        UserDto responseDto = userService.getUserByUsername(username);
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping
    public ResponseEntity<UserDto> updateCurrentUser(
            @RequestHeader("X-Username") String username,
            @RequestBody @Valid UpdateUserDto requestDto,
            BindingResult result
    ) {
        validateErrors(result);
        UserDto responseDto = userService.updateUserByUsername(username, requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @PatchMapping
    public ResponseEntity<UserDto> patchCurrentUser(
            @RequestHeader("X-Username") String username,
            @RequestBody UpdateUserDto requestDto
    ) {
        UserDto responseDtp = userService.patchUserByUsername(username, requestDto);
        return ResponseEntity.ok(responseDtp);
    }

    @DeleteMapping
    public ResponseEntity<ResponseMessage> deleteCurrentUser(
            @RequestHeader("X-Username") String username
    ) {
        userService.deleteUserByUsername(username);
        return ResponseEntity.noContent().build();
    }

    private void validateErrors(BindingResult result) { //TODO: to bean or library
        if (result.hasErrors()) {
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