package com.justbelieveinmyself.userservice.services;

import com.justbelieveinmyself.library.dto.EmailUpdateDto;
import com.justbelieveinmyself.library.enums.Role;
import com.justbelieveinmyself.library.exception.NotFoundException;
import com.justbelieveinmyself.userservice.domains.dtos.UpdateUserDto;
import com.justbelieveinmyself.userservice.domains.dtos.UserDto;
import com.justbelieveinmyself.userservice.domains.entities.User;
import com.justbelieveinmyself.userservice.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;
    @Test
    void createNewUser() {
        UserDto dto = UserDto.builder().build();
        userService.createNewUser(dto);

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void updateUserById() {
        final long userId = 1L;
        final String firstName = "first";
        final String lastName = "last";
        final String phone = "+79718009324";
        final String username = "user";
        final String email = "email@email.com";
        Set<Role> roles = new HashSet<>(List.of(Role.STUDENT));
        User user = User.builder()
                .id(userId)
                .username(username)
                .firstName("old")
                .lastName("old")
                .phone("old")
                .roles(roles)
                .email(email)
                .build();

        UpdateUserDto updateDto = UpdateUserDto.builder()
                .firstName(firstName)
                .lastName(lastName)
                .phone(phone)
                .build();


        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        UserDto updatedUserDto = userService.updateUserById(userId, updateDto);

        assertEquals(updatedUserDto.getId(), userId);
        assertEquals(updatedUserDto.getFirstName(), firstName);
        assertEquals(updatedUserDto.getLastName(), lastName);
        assertEquals(updatedUserDto.getPhone(), phone);
        assertEquals(updatedUserDto.getUsername(), username);
        assertEquals(updatedUserDto.getEmail(), email);
        assertEquals(updatedUserDto.getRoles(), roles);
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void deleteUserById() {
        final long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.of(new User()));

        userService.deleteUserById(userId);

        verify(userRepository, times(1)).delete(any(User.class));
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void patchUserById() {
        final long userId = 1L;
        final String firstName = "first";
        final String lastName = null;
        final String phone = "+79718009324";
        final String username = "user";
        final String email = "email@email.com";
        Set<Role> roles = new HashSet<>(List.of(Role.STUDENT));

        User user = User.builder()
                .id(userId)
                .username(username)
                .firstName("old")
                .lastName("old")
                .phone("old")
                .email(email)
                .roles(roles)
                .build();

        UpdateUserDto updateDto = UpdateUserDto.builder()
                .firstName(firstName)
                .lastName(lastName)
                .phone(phone)
                .build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        UserDto patchedUserDto = userService.patchUserById(userId, updateDto);

        assertEquals(patchedUserDto.getId(), userId);
        assertEquals(patchedUserDto.getFirstName(), firstName);
        assertEquals(patchedUserDto.getLastName(), "old");
        assertEquals(patchedUserDto.getPhone(), phone);
        assertEquals(patchedUserDto.getUsername(), username);
        assertEquals(patchedUserDto.getEmail(), email);
        assertEquals(patchedUserDto.getRoles(), roles);
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void findById_whenDoesNotExist() {
        final long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.findById(userId), "User not found with UserID: " + userId);
    }

    @Test
    void findById_whenExist() {
        final long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.of(User.builder().id(userId).build()));

        User user = userService.findById(userId);

        assertEquals(user.getId(), userId);
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void updateEmail() {
        final long userId = 1L;
        final String email = "email@email.com";
        final String firstName = "first";
        final String lastName = "last";
        final String phone = "+79718009324";
        final String username = "user";
        Set<Role> roles = new HashSet<>(List.of(Role.STUDENT));

        User user = User.builder()
                .id(userId)
                .username(username)
                .firstName(firstName)
                .lastName(lastName)
                .phone(phone)
                .email("old")
                .roles(roles)
                .build();

        EmailUpdateDto dto = EmailUpdateDto.builder()
                .userId(userId)
                .email(email).build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        userService.updateEmail(dto);

        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(user);
        assertEquals(user.getEmail(), email);
        assertEquals(user.getFirstName(), firstName);
        assertEquals(user.getLastName(), lastName);
        assertEquals(user.getRoles(), roles);
        assertEquals(user.getUsername(), username);
        assertEquals(user.getId(), userId);
        assertEquals(user.getPhone(), phone);
    }

    @Test
    void getUsers() {
        List<UserDto> users = userService.getUsers();

        verify(userRepository, times(1)).findAll();
    }
}