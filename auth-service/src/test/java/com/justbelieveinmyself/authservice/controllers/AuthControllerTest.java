package com.justbelieveinmyself.authservice.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.justbelieveinmyself.authservice.apiclients.CourseServiceClient;
import com.justbelieveinmyself.authservice.apiclients.MailServiceClient;
import com.justbelieveinmyself.authservice.apiclients.UserServiceClient;
import com.justbelieveinmyself.authservice.domains.dtos.LoginRequestDto;
import com.justbelieveinmyself.authservice.domains.dtos.RefreshResponseDto;
import com.justbelieveinmyself.authservice.domains.dtos.RegisterDto;
import com.justbelieveinmyself.authservice.domains.dtos.UserDto;
import com.justbelieveinmyself.authservice.services.AuthService;
import com.justbelieveinmyself.authservice.services.RefreshTokenService;
import com.justbelieveinmyself.library.enums.Role;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.Instant;
import java.util.Set;

import static org.hamcrest.Matchers.matchesPattern;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
@EmbeddedKafka(ports = {9092})
class AuthControllerTest {
    @InjectMocks
    private AuthController authController;
    @MockBean
    private AuthService authService;
    @MockBean
    private UserServiceClient userServiceClient;
    @MockBean
    private CourseServiceClient courseServiceClient;
    @MockBean
    private MailServiceClient mailServiceClient;
    @MockBean
    private RefreshTokenService refreshTokenService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("On register must call authService.register() and return UserDto")
    void register() throws Exception {
        Long id = 1L;
        String username = "username";
        String email = "email@email.com";
        String phone = "+79718009324";
        Set<Role> roles = Set.of(Role.STUDENT);

        RegisterDto registerDto = RegisterDto.builder()
                .username(username)
                .email(email)
                .phone(phone)
                .password("1234").build();

        UserDto userDto = UserDto.builder()
                .id(id)
                .username(username)
                .email(email)
                .phone(phone)
                .roles(roles)
                .build();

        when(authService.register(any(RegisterDto.class))).thenReturn(userDto);

        String registerDtoJson = objectMapper.writeValueAsString(registerDto);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(registerDtoJson))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.username").value(username))
                .andExpect(jsonPath("$.email").value(email))
                .andExpect(jsonPath("$.phone").value(phone))
                .andExpect(jsonPath("$.roles[0]").value(Role.STUDENT.toString()));

        verify(authService, times(1)).register(any(RegisterDto.class));
    }

    @Test
    @DisplayName("On login must call authService.login() and return RefreshResponseDto")
    void login() throws Exception {
        String username = "username";
        String refreshToken = "refreshToken";
        Instant refreshTokenExpiration = Instant.now();
        String accessToken = "accessToken";
        Instant accessTokenExpiration = Instant.now().plusMillis(2000);

        LoginRequestDto loginRequestDto = LoginRequestDto.builder()
                .username(username)
                .password("1234").build();

        RefreshResponseDto refreshResponseDto = RefreshResponseDto.builder()
                .refreshToken(refreshToken)
                .refreshTokenExpiration(refreshTokenExpiration)
                .accessToken(accessToken)
                .accessTokenExpiration(accessTokenExpiration).build();

        when(authService.login(any(LoginRequestDto.class))).thenReturn(refreshResponseDto);

        String loginRequestDtoJson = objectMapper.writeValueAsString(loginRequestDto);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginRequestDtoJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.refreshToken").value(refreshToken))
                .andExpect(jsonPath("$.refreshTokenExpiration", matchesPattern("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}")))
                .andExpect(jsonPath("$.accessToken").value(accessToken))
                .andExpect(jsonPath("$.accessTokenExpiration", matchesPattern("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}")));

        verify(authService, times(1)).login(any(LoginRequestDto.class));
    }

    @Test
    @DisplayName("On register must call authService.register() and return UserDto")
    void refresh() {
    }
}
