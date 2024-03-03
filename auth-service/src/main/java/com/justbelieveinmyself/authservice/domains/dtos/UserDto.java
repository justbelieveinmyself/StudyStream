package com.justbelieveinmyself.authservice.domains.dtos;

import com.justbelieveinmyself.authservice.domains.entities.User;
import com.justbelieveinmyself.authservice.domains.enums.Role;
import com.justbelieveinmyself.library.dto.Dto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.Set;

@Getter
@Setter
public class UserDto implements Dto<User> {
    private String username;
    private String password;
    private String email;
    private Set<Role> roles;
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
