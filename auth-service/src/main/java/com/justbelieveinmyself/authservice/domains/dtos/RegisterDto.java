package com.justbelieveinmyself.authservice.domains.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class RegisterDto {
    @NotBlank(message = "Please, enter your username!")
    private String username;
    @NotBlank(message = "Please, enter your password!")
    private String password;
    @Email(message = "Invalid email!")
    @NotBlank(message = "Please, enter your email!")
    private String email;
    private String firstName;
    private String lastName;
    @Pattern(regexp = "\\+\\d{11}", message = "Invalid phone format! ex. +79718009324.")
    private String phone;
}
