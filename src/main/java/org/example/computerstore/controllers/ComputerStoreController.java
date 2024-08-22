package org.example.computerstore.controllers;

import org.example.computerstore.dto.ComputerUserDTO;
import org.example.computerstore.entities.ComputerUser;
import org.example.computerstore.services.ComputerUserAccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ComputerStoreController {

    private final ComputerUserAccountService computerUserAccountService;

    public ComputerStoreController(final ComputerUserAccountService computerUserAccountService) {
        this.computerUserAccountService = computerUserAccountService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> createUser(@RequestBody ComputerUserDTO user) {
        ComputerUser response = computerUserAccountService.registerComputerUser(user);
        return new ResponseEntity<>(response.getUsername(), HttpStatus.CREATED);
    }

    @GetMapping("/test")
    public String test() {
        return "This is a test";
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody ComputerUserDTO user) {
        String response = computerUserAccountService.loginUser(user);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/api/spring")
    public ResponseEntity<String> api() {
        return new ResponseEntity<>("This is a api", HttpStatus.OK);
    }
}
