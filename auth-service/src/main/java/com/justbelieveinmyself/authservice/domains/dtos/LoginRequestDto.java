package com.justbelieveinmyself.authservice.domains.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class LoginRequestDto {
    @NotBlank(message = "")
    private String username;
    @NotBlank(message = "")
    private String password;
}
