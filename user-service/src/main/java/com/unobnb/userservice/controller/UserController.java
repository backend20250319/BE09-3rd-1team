package com.unobnb.userservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("/users")
    public ResponseEntity<Void> getUser() {
        System.out.println("testest");
        return ResponseEntity.ok(null);
    }
}
