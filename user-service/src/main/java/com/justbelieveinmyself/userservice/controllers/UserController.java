package com.justbelieveinmyself.userservice.controllers;

import com.justbelieveinmyself.library.aspects.ValidateErrors;
import com.justbelieveinmyself.library.dto.ResponseMessage;
import com.justbelieveinmyself.userservice.domains.dtos.UpdateUserDto;
import com.justbelieveinmyself.userservice.domains.dtos.UserDto;
import com.justbelieveinmyself.userservice.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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
    @ValidateErrors
    public ResponseEntity<UserDto> updateUserById(
            @PathVariable Long userId,
            @RequestBody @Valid UpdateUserDto requestDto,
            BindingResult result
    ) {
        UserDto responseDto = userService.updateUserById(userId, requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping
    public ResponseEntity<UserDto> getCurrentUser(@RequestHeader("X-User-Id") Long userId) {
        UserDto responseDto = userService.getUserById(userId);
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping
    @ValidateErrors
    public ResponseEntity<UserDto> updateCurrentUser(
            @RequestHeader("X-User-Id") Long userId,
            @RequestBody @Valid UpdateUserDto requestDto,
            BindingResult result
    ) {
        UserDto responseDto = userService.updateUserById(userId, requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @PatchMapping
    public ResponseEntity<UserDto> patchCurrentUser(
            @RequestHeader("X-User-Id") Long userId,
            @RequestBody UpdateUserDto requestDto
    ) {
        UserDto responseDtp = userService.patchUserById(userId, requestDto);
        return ResponseEntity.ok(responseDtp);
    }

    @DeleteMapping //TODO: delete mapping by id only by admin. need to validate is this user is current? i think probably
    public ResponseEntity<ResponseMessage> deleteCurrentUser(
            @RequestHeader("X-User-Id") Long userId
    ) {
        userService.deleteUserById(userId);
        return ResponseEntity.noContent().build();
    }

}

// TODO: swagger api
// @RequestHeader("X-Username") String username, @RequestHeader("X-User-Roles") String[] roles