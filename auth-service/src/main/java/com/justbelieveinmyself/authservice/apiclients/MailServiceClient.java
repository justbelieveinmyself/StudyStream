package com.justbelieveinmyself.authservice.apiclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("mail-service")
public interface MailServiceClient {
    @PostMapping("api/v1/users")
    void createUser(@RequestParam("userId") Long userId, @RequestParam("email") String email, @RequestParam("username") String username);
}