package com.justbelieveinmyself.authservice.domains.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class RegisterDto {
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;
    @NotBlank(message = "Please, enter your username!")
    private String username;
    @NotBlank(message = "Please, enter your password!")
    private String password;
    @Email(message = "Invalid email!")
    @NotBlank(message = "Please, enter your email!")
    private String email;
    private String firstName;
    private String lastName;
    @Pattern(regexp = "\\+\\d{11}", message = "Invalid phone format! ex. +7978009324.")
    private String phone;
}
