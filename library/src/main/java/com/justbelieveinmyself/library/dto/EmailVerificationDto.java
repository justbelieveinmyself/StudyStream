package com.justbelieveinmyself.library.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class EmailVerificationDto {
    private String username;
    private String email;
    private String activationCode;
}
