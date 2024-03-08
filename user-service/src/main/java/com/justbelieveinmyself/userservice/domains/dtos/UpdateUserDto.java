package com.justbelieveinmyself.userservice.domains.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateUserDto {
    @NotBlank(message = "Please, enter your firstName!")
    private String firstName;
    @NotBlank(message = "Please, enter your lastName")
    private String lastName;
    @NotBlank(message = "Please, enter your phone! ex. +7978009324.")
    private String phone; // TODO: custom validator phone number
    //other details
}
