package com.justbelieveinmyself.mailservice.domains.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmailVerificationDto {
    private String username;
    private String email;
    private String activationCode;
}
