package com.justbelieveinmyself.authservice.domains.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegisterDto {
    @NotBlank(message = "Please, enter your username!")
    private String username;
    @NotBlank(message = "Please, enter your password!")
    private String password;
    @Email(message = "Please, enter your email!")
    private String email;
}
