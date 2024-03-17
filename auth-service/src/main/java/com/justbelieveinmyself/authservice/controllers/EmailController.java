package com.justbelieveinmyself.authservice.controllers;

import com.justbelieveinmyself.authservice.domains.dtos.EmailDto;
import com.justbelieveinmyself.authservice.services.EmailService;
import com.justbelieveinmyself.library.exception.InvalidRequestException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/email")
@RequiredArgsConstructor
public class EmailController {
    private final EmailService emailService;

    @PatchMapping
    public ResponseEntity<String> updateEmail(
            @RequestBody @Valid EmailDto requestDto,
            BindingResult result,
            @RequestHeader("X-User-Id") Long userId
    ) {
        validateErrors(result);
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
