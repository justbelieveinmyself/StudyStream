package com.justbelieveinmyself.userservice.controllers;

import com.justbelieveinmyself.library.aspects.ValidateErrors;
import com.justbelieveinmyself.library.dto.ResponseMessage;
import com.justbelieveinmyself.library.exception.ForbiddenException;
import com.justbelieveinmyself.userservice.domains.annotations.RequiresRoleOrSelf;
import com.justbelieveinmyself.userservice.domains.dtos.UpdateUserDto;
import com.justbelieveinmyself.userservice.domains.dtos.UserDto;
import com.justbelieveinmyself.userservice.services.UserService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping(value = "/api/v1/user")
@CrossOrigin
@RequiredArgsConstructor
@Tag(name = "User API", description = "Interaction with users")
@SecurityRequirement(name = "Bearer Authentication")
public class UserController {
    private final UserService userService;

    @Operation(summary = "Get User by ID", description = "Get User by ID")
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long userId) {
        UserDto responseDto = userService.getUserById(userId);
        return ResponseEntity.ok(responseDto);
    }

    @Operation(summary = "Update User by ID", description = "Update User by ID")
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

    @Operation(summary = "Delete User by ID", description = "Delete User by ID")
    @DeleteMapping("/{userId}")
    @RequiresRoleOrSelf(roles = "ADMIN")
    public ResponseEntity<ResponseMessage> deleteUserById(
            @Parameter(hidden = true) @RequestHeader("X-User-Id") Long currentUserId,
            @Parameter(hidden = true) @RequestHeader("X-User-Roles") String[] currentUserRoles,
            @PathVariable Long userId
    ) {
        userService.deleteUserById(userId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get Current User", description = "Get Current User by Authentication")
    @GetMapping
    public ResponseEntity<UserDto> getCurrentUser(@Parameter(hidden = true) @RequestHeader("X-User-Id") Long userId) {
        UserDto responseDto = userService.getUserById(userId);
        return ResponseEntity.ok(responseDto);
    }

    @Operation(summary = "Update Current User", description = "Update Current User by Authentication")
    @PutMapping
    @ValidateErrors
    public ResponseEntity<UserDto> updateCurrentUser(
            @Parameter(hidden = true) @RequestHeader("X-User-Id") Long userId,
            @RequestBody @Valid UpdateUserDto requestDto,
            BindingResult result
    ) {
        UserDto responseDto = userService.updateUserById(userId, requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @Operation(summary = "Partial Update Current User", description = "Partial Update Current User by Authentication")
    @PatchMapping
    public ResponseEntity<UserDto> patchCurrentUser(
            @Parameter(hidden = true) @RequestHeader("X-User-Id") Long userId,
            @RequestBody UpdateUserDto requestDto
    ) {
        UserDto responseDtp = userService.patchUserById(userId, requestDto);
        return ResponseEntity.ok(responseDtp);
    }

    @Operation(summary = "Delete Current User", description = "Delete Current User by Authentication")
    @DeleteMapping //TODO: delete mapping by id only by admin. need to validate is this user is current? i think probably
    public ResponseEntity<ResponseMessage> deleteCurrentUser(
            @Parameter(hidden = true) @RequestHeader("X-User-Id") Long userId
    ) {
        userService.deleteUserById(userId);
        return ResponseEntity.noContent().build();
    }

}
//TODO: one controller for current user, another for list and by id
//TODO: admin

// @RequestHeader("X-Username") String username, @RequestHeader("X-User-Roles") String[] roles