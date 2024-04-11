package com.justbelieveinmyself.userservice.services;

import com.justbelieveinmyself.library.dto.ModelUtils;
import com.justbelieveinmyself.library.exception.NotFoundException;
import com.justbelieveinmyself.userservice.domains.dtos.EmailUpdateDto;
import com.justbelieveinmyself.userservice.domains.dtos.UpdateUserDto;
import com.justbelieveinmyself.userservice.domains.dtos.UserDto;
import com.justbelieveinmyself.userservice.domains.entities.User;
import com.justbelieveinmyself.userservice.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserDto getUserById(Long userId) {
        User user = findById(userId);
        return new UserDto().fromEntity(user);
    }

    public void createNewUser(UserDto user) {
        userRepository.save(user.toEntity());
    }

    public UserDto updateUserById(Long userId, UpdateUserDto dto) {
        User user = findById(userId);

        ModelUtils.copyProperties(dto, user);

        User savedUser = userRepository.save(user);
        return new UserDto().fromEntity(savedUser);
    }

    public void deleteUserById(Long userId) {
        User user = findById(userId);
        userRepository.delete(user);
    }

    public UserDto patchUserById(Long userId, UpdateUserDto dto) {
        User user = findById(userId);

        ModelUtils.copyPropertiesIgnoreNull(dto, user);

        User savedUser = userRepository.save(user);
        return new UserDto().fromEntity(savedUser);
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found with UserID: " + id));
    }

    public void updateEmail(EmailUpdateDto emailUpdateDto) {
        User user = findById(emailUpdateDto.getUserId());
        user.setEmail(emailUpdateDto.getEmail());
        userRepository.save(user);
    }

    public List<UserDto> getUsers() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDtoList = users.stream().map((user) -> new UserDto().fromEntity(user)).collect(Collectors.toList());
        return userDtoList;
    }

}

