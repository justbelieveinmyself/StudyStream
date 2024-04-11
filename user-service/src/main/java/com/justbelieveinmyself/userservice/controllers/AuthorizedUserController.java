package com.justbelieveinmyself.userservice.controllers;

import com.justbelieveinmyself.library.aspects.ValidateErrors;
import com.justbelieveinmyself.library.dto.ResponseMessage;
import com.justbelieveinmyself.userservice.domains.dtos.UpdateUserDto;
import com.justbelieveinmyself.userservice.domains.dtos.UserDto;
import com.justbelieveinmyself.userservice.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/user")
@CrossOrigin
@RequiredArgsConstructor
@Tag(name = "Authorized User API", description = "Interaction with authorized user")
@SecurityRequirement(name = "Bearer Authentication")
@ApiResponses({
        @ApiResponse(responseCode = "503", description = "Service unavailable")
}) // TODO: OpenApiRestController with api responses
public class AuthorizedUserController {
    private final UserService userService;

    @Operation(summary = "Get Current User", description = "Get Current User by Authentication", responses = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Requires authentication"),
    })
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
    @DeleteMapping
    public ResponseEntity<ResponseMessage> deleteCurrentUser(
            @Parameter(hidden = true) @RequestHeader("X-User-Id") Long userId
    ) {
        userService.deleteUserById(userId);
        return ResponseEntity.noContent().build();
    }

}