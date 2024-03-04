package com.justbelieveinmyself.authservice.domains.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class RefreshRequestDto {
    @NotBlank(message = "Please, enter your refreshToken!")
    private String refreshToken;
}
