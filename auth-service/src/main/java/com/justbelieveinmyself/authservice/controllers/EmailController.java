package com.justbelieveinmyself.authservice.controllers;

import com.justbelieveinmyself.authservice.domains.dtos.EmailDto;
import com.justbelieveinmyself.authservice.services.EmailService;
import com.justbelieveinmyself.library.aspects.ValidateErrors;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/email")
@RequiredArgsConstructor
public class EmailController {
    private final EmailService emailService;

    @PatchMapping
    @ValidateErrors
    public ResponseEntity<String> updateEmail(
            @RequestBody @Valid EmailDto requestDto,
            BindingResult result,
            @RequestHeader("X-User-Id") Long userId
    ) {
        emailService.updateEmail(userId, requestDto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<String> verifyEmail(
            @RequestParam("code") @NotBlank String activationCode
    ) {
        emailService.verifyEmail(activationCode);
        return ResponseEntity.noContent().build();
    }

}
