package com.justbelieveinmyself.authservice.apiclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "course-service")
public interface CourseServiceClient {
    @PostMapping("/api/v1/users")
    void createUser(Long userId);
}
