package com.justbelieveinmyself.authservice.domains.dtos;

import com.justbelieveinmyself.authservice.domains.annotations.PasswordMatching;
import com.justbelieveinmyself.authservice.domains.annotations.StrongPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@PasswordMatching(password = "password", confirmPassword = "confirmPassword")
public class RegisterDto {
    @NotBlank(message = "Please, enter your username!")
    private String username;
    @NotBlank(message = "Please, enter your password!")
    @StrongPassword
    private String password;
    private String confirmPassword;
    @Email(message = "Invalid email!")
    @NotBlank(message = "Please, enter your email!")
    private String email;
    private String firstName;
    private String lastName;
    @Pattern(regexp = "\\+\\d{11}", message = "Invalid phone format! ex. +79718009324.")
    private String phone;
}
