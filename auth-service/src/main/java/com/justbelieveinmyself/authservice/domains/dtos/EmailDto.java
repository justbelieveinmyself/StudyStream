package com.justbelieveinmyself.authservice.domains.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class EmailDto {
    @Email(message = "Invalid email!")
    @NotBlank(message = "Please, enter your email!")
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
