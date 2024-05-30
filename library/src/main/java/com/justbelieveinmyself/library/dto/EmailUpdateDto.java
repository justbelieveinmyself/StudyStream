package com.justbelieveinmyself.library.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class EmailUpdateDto {
    private Long userId;
    private String email;
}
