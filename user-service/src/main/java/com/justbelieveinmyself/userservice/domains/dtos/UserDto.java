package com.justbelieveinmyself.userservice.domains.dtos;

import com.justbelieveinmyself.library.dto.Dto;
import com.justbelieveinmyself.library.enums.Role;
import com.justbelieveinmyself.userservice.domains.entities.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.beans.BeanUtils;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto implements Dto<User> {
    private Long id;
    private String username;
    private String email;
    private Set<Role> roles;
    private String firstName;
    private String lastName;
    private String phone;

    @Override
    public UserDto fromEntity(User user) {
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);
        return userDto;
    }

    @Override
    public User toEntity() {
        User user = new User();
        BeanUtils.copyProperties(this, user);
        return user;
    }
}
