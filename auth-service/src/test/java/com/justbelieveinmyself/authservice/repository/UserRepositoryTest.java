package com.justbelieveinmyself.authservice.repository;

import com.justbelieveinmyself.authservice.domains.entities.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:h2:mem:db;DB_CLOSE_DELAY=-1;",
        "spring.datasource.username=sa",
        "spring.datasource.password=sa",
        "spring.datasource.driver-class-name=org.h2.Driver",
        "spring.jpa.show-sql=true",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("existsByUsernameOrEmail: when User with Username already Exist in DB")
    void existsByUsernameOrEmail_whenUsernameAlreadyExist() {
        final String username = "username";
        User user = new User();
        user.setEmail("my@mail.com");
        user.setUsername(username);
        user.setPassword("123");
        userRepository.save(user);

        boolean result = userRepository.existsByUsernameOrEmail(username, "random@mail.da");

        assertTrue(result);
    }

    @Test
    @DisplayName("existsByUsernameOrEmail: when User with Email already Exist in DB")
    void existsByUsernameOrEmail_whenEmailAlreadyExist() {
        final String email = "my@mail.com";
        User user = new User();
        user.setEmail(email);
        user.setUsername("username");
        user.setPassword("123");
        userRepository.save(user);

        boolean result = userRepository.existsByUsernameOrEmail("random", email);

        assertTrue(result);
    }

    @Test
    @DisplayName("existsByUsernameOrEmail: when User with email or username doesn't exist")
    void existsByUsernameOrEmail_whenDoesntExist() {
        boolean result = userRepository.existsByUsernameOrEmail("random", "random");

        assertFalse(result);
    }

    @Test
    @DisplayName("existsByEmail: when User with Email already exist")
    void existsByEmail_whenEmailAlreadyExist() {
        final String email = "my@mail.com";
        User user = new User();
        user.setEmail(email);
        user.setUsername("username");
        user.setPassword("123");
        userRepository.save(user);

        boolean result = userRepository.existsByEmail(email);

        assertTrue(result);
    }

    @Test
    @DisplayName("existsByEmail: when User with Email doesn't exist")
    void existsByEmail_whenDoesntExist() {
        final String email = "my@mail.com";
        User user = new User();
        user.setEmail(email);
        user.setUsername("username");
        user.setPassword("123");
        userRepository.save(user);

        boolean result = userRepository.existsByEmail(email);

        assertTrue(result);
    }

    @Test
    @DisplayName("findByUsername")
    void findByUsername() {
        final String username = "username";
        User user = new User();
        user.setEmail("my@mail.com");
        user.setUsername(username);
        user.setPassword("123");
        userRepository.save(user);

        Optional<User> userOptional = userRepository.findByUsername(username);

        assertTrue(userOptional.isPresent());
        assertEquals(user, userOptional.get());
    }

    @Test
    @DisplayName("findByActivationCode")
    void findByActivationCode() {
        String code = UUID.randomUUID().toString();
        User user = new User();
        user.setEmail("my@mail.com");
        user.setUsername("username");
        user.setPassword("123");
        user.setActivationCode(code);
        userRepository.save(user);

        Optional<User> userOptional = userRepository.findByActivationCode(code);

        assertTrue(userOptional.isPresent());
        assertEquals(user, userOptional.get());
    }

}