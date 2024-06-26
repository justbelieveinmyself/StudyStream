package com.justbelieveinmyself.userservice.domains.dtos;

import com.justbelieveinmyself.library.dto.Dto;
import com.justbelieveinmyself.library.dto.ModelUtils;
import com.justbelieveinmyself.library.enums.Role;
import com.justbelieveinmyself.userservice.domains.entities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
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
        ModelUtils.copyProperties(user, userDto);
        return userDto;
    }

    @Override
    public User toEntity() {
        User user = new User();
        ModelUtils.copyProperties(this, user);
        return user;
    }
}
