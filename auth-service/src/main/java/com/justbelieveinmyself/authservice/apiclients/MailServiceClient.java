package com.justbelieveinmyself.authservice.apiclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("mail-service")
public interface MailServiceClient {
    @PostMapping("api/v1/users")
    void createUser(@RequestParam("userId") Long userId, @RequestParam("email") String email, @RequestParam("username") String username);
}
//TODO: change email with kafka: UserService update email -> mail-service sends confirmation
// on confirm userservice -> sends message with new email -> auth,mail services updating email

// TODO: delete useless kafka endpoints