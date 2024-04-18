/*
package com.justbelieveinmyself.authservice.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.justbelieveinmyself.authservice.domains.dtos.RegisterDto;
import com.justbelieveinmyself.authservice.domains.dtos.UserDto;
import com.justbelieveinmyself.authservice.services.AuthService;
import com.justbelieveinmyself.authservice.services.RefreshTokenService;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
@TestPropertySource(properties = {
        "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration"
})
class AuthControllerTest {

    @InjectMocks
    private AuthController authController;
    @Mock
    private AuthService authService;
    @Mock
    private RefreshTokenService refreshTokenService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("On register must call authService.register() and return UserDto")
    void register() throws Exception {
        RegisterDto registerDto = RegisterDto.builder()
                .username("username")
                .email("email@mail.com")
                .phone("+79718009324")
                .password("1234").build();
        UserDto userDto = new UserDto();

        String registerDtoJson = objectMapper.writeValueAsString(registerDto);
        when(authService.register(any())).thenReturn(userDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(registerDtoJson))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L));

        verify(authService, times(1)).register(any());
    }

    @Test
    void login() {
    }

    @Test
    void refresh() {
    }
}
*/
