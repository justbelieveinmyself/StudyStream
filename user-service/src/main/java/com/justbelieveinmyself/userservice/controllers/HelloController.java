package com.justbelieveinmyself.userservice.controllers;

import jakarta.ws.rs.HeaderParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/hello")
public class HelloController {
    @GetMapping("/world")
    public ResponseEntity<ArrayList<String>> hello(@RequestHeader("X-Username") String username, @RequestHeader("X-User-Roles") String[] roles) {
        ArrayList<String> headers = new ArrayList<>(List.of(roles));
        headers.add(username);
        return ResponseEntity.ok(headers);
    }
}
