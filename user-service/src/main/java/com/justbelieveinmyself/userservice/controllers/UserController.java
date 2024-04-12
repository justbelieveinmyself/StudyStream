package com.justbelieveinmyself.userservice.controllers;

import com.justbelieveinmyself.library.aspects.ValidateErrors;
import com.justbelieveinmyself.library.dto.ResponseMessage;
import com.justbelieveinmyself.library.aspects.RequiresRoleOrSelf;
import com.justbelieveinmyself.userservice.domains.dtos.UpdateUserDto;
import com.justbelieveinmyself.userservice.domains.dtos.UserDto;
import com.justbelieveinmyself.userservice.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/users")
@CrossOrigin
@RequiredArgsConstructor
@Tag(name = "Users API", description = "Interaction with users")
@SecurityRequirement(name = "Bearer Authentication")
public class UserController {
    private final UserService userService;

    @Operation(summary = "Get Users", description = "Get All Users")
    @GetMapping
    public ResponseEntity<List<UserDto>> getUsers() {
        List<UserDto> responseDto = userService.getUsers();
        return ResponseEntity.ok(responseDto);
    }

    @Operation(summary = "Get User by ID", description = "Get User by ID")
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long userId) {
        UserDto responseDto = userService.getUserById(userId);
        return ResponseEntity.ok(responseDto);
    }

    @Operation(summary = "Update User by ID", description = "Update User by ID")
    @PutMapping("/{userId}")
    @ValidateErrors
    @RequiresRoleOrSelf(roles = "ADMIN")
    public ResponseEntity<UserDto> updateUserById(
            @Parameter(hidden = true) @RequestHeader("X-User-Id") Long currentUserId,
            @Parameter(hidden = true) @RequestHeader("X-User-Roles") String[] currentUserRoles,
            @PathVariable Long userId,
            @RequestBody @Valid UpdateUserDto requestDto,
            BindingResult result
    ) {
        UserDto responseDto = userService.updateUserById(userId, requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @Operation(summary = "Partial Update User by ID", description = "Partial Update User by ID")
    @PatchMapping("/{userId}")
    @RequiresRoleOrSelf(roles = "ADMIN")
    public ResponseEntity<UserDto> patchUserById(
            @Parameter(hidden = true) @RequestHeader("X-User-Id") Long currentUserId,
            @Parameter(hidden = true) @RequestHeader("X-User-Roles") String[] currentUserRoles,
            @PathVariable Long userId,
            @RequestBody UpdateUserDto dto
    ) {
        UserDto userDto = userService.patchUserById(userId, dto);
        return ResponseEntity.ok(userDto);
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

}