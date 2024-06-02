package com.justbelieveinmyself.userservice.services;

import com.justbelieveinmyself.library.enums.Role;
import com.justbelieveinmyself.userservice.domains.dtos.UpdateUserDto;
import com.justbelieveinmyself.userservice.domains.dtos.UserDto;
import com.justbelieveinmyself.userservice.domains.entities.User;
import com.justbelieveinmyself.userservice.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;
    @Test
    void createNewUser() {
        userService.createNewUser(any(UserDto.class));

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void updateUserById() {
        final Long userId = 1L;
        final String firstName = "first";
        final String lastName = "last";
        final String phone = "+79718009324";
        final String username = "user";
        Set<Role> roles = new HashSet<>(Arrays.asList(Role.STUDENT));
        UserDto.builder()
                .id(userId)
                .username(username)
                .firstName("old")
                .lastName("old")
                .phone("old")
                .roles(roles)
                .build();

        UpdateUserDto updateDto = UpdateUserDto.builder()
                .firstName(firstName)
                .lastName(lastName)
                .phone(phone)
                .build();

        UserDto updatedUserDto = userService.updateUserById(userId, updateDto);
        assertEquals(updatedUserDto.getId(), userId);
        assertEquals(updatedUserDto.getFirstName(), firstName);
        assertEquals(updatedUserDto.getLastName(), lastName);
        assertEquals(updatedUserDto.getPhone(), phone);
    }

    @Test
    void deleteUserById() {
    }

    @Test
    void patchUserById() {
    }

    @Test
    void findById() {
    }

    @Test
    void updateEmail() {
    }

    @Test
    void getUsers() {
    }
}