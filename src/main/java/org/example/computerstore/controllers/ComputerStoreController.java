package org.example.computerstore.controllers;

import jakarta.validation.Valid;
import org.example.computerstore.dto.UserJwtResponseDTO;
import org.example.computerstore.dto.UserRequestDTO;
import org.example.computerstore.dto.UserResponseDTO;
import org.example.computerstore.entities.ComputerUser;
import org.example.computerstore.services.ComputerUserAccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ComputerStoreController {

    private final ComputerUserAccountService computerUserAccountService;

    public ComputerStoreController(final ComputerUserAccountService computerUserAccountService) {
        this.computerUserAccountService = computerUserAccountService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserRequestDTO user) {
        ComputerUser response = computerUserAccountService.registerComputerUser(user);
        return new ResponseEntity<>(new UserResponseDTO(response.getUsername()), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<UserJwtResponseDTO> login(@Valid @RequestBody UserRequestDTO user) {
        Optional<String> response = computerUserAccountService.loginUser(user);
        return response.map(s ->
                new ResponseEntity<>(new UserJwtResponseDTO(s), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.UNAUTHORIZED));
    }
}
