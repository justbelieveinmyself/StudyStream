package com.justbelieveinmyself.authservice.apiclients;

import com.justbelieveinmyself.authservice.domains.dtos.UserDto;
import com.justbelieveinmyself.library.dto.EmailUpdateDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "user-service")
public interface UserServiceClient {
    @PostMapping("api/v1/users")
    void createUser(UserDto user);

    @PutMapping("api/v1/user/email")
    void updateEmail(EmailUpdateDto emailUpdateDto);
}
