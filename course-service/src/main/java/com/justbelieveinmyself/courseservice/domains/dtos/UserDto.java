package com.justbelieveinmyself.courseservice.domains.dtos;

import com.justbelieveinmyself.courseservice.domains.entities.User;
import com.justbelieveinmyself.library.dto.Dto;
import com.justbelieveinmyself.library.enums.Role;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.Set;

@Getter
@Setter
public class UserDto {
    private Long id;
    private String username;
    private String email;
    private Set<Role> roles;
    private String firstName;
    private String lastName;
    private String phone;

}
