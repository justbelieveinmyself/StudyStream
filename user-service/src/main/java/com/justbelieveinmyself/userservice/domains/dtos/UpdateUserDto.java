package com.justbelieveinmyself.userservice.domains.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
    @NotBlank(message = "Please, enter your phone!")
    @Pattern(regexp = "\\+\\d{11}", message = "Invalid phone format! ex. +7978009324.")
    private String phone;
    //other details
}
