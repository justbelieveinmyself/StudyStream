package com.justbelieveinmyself.authservice.domains.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class RefreshRequestDto {
    @NotBlank(message = "Please, enter your refreshToken!")
    private String refreshToken;
}
