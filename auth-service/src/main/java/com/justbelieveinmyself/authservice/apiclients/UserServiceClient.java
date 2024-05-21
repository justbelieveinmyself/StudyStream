package com.justbelieveinmyself.authservice.apiclients;

import com.justbelieveinmyself.authservice.domains.dtos.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "user-service")
public interface UserServiceClient {
    @PostMapping("api/v1/users")
    void createUser(UserDto user);
}
