package services;

import domains.dtos.LoginRequestDto;
import domains.dtos.LoginResponseDto;
import domains.dtos.RegisterDto;
import domains.dtos.UserDto;
import domains.entities.User;
import exceptions.UsernameOrEmailAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;

    public UserDto register(RegisterDto registerDto) {
        if (userRepository.existsByUsernameOrEmail()) {
            throw new UsernameOrEmailAlreadyExistsException("User with username or email already exists!");
        }
        User user = new User();
        user.setUsername(registerDto.getUsername());
        user.setPassword(registerDto.getPassword());
        User savedUser = userRepository.save(user);
        return new UserDto().fromEntity(savedUser);
    }

    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        return null;
    }
}
