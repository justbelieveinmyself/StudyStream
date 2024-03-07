package com.justbelieveinmyself.authservice.domains.dtos;

import com.justbelieveinmyself.library.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
@Getter
@Setter
public class RegisterDto {
    private Long id;
    @NotBlank(message = "Please, enter your username!")
    private String username;
    @NotBlank(message = "Please, enter your password!")
    private String password;
    @Email(message = "Invalid email!")
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
}
