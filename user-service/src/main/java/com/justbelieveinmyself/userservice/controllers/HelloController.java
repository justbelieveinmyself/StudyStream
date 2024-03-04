package com.justbelieveinmyself.userservice.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/hello")
public class HelloController {
    @GetMapping("/world")
    public String hello() {
        return "hello";
    }
}
