package org.example.computerstore.controllers;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import org.example.computerstore.dto.ComputerUserDTO;
import org.example.computerstore.dto.UserRequestDTO;
import org.example.computerstore.entities.ComputerUser;
import org.example.computerstore.services.ComputerUserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Validator;


import java.util.Set;

@RestController
@RequestMapping("/api")
public class ComputerStoreController {

    private final ComputerUserAccountService computerUserAccountService;

    @Autowired
    private Validator validator;

    public ComputerStoreController(final ComputerUserAccountService computerUserAccountService) {
        this.computerUserAccountService = computerUserAccountService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> createUser(@Valid @RequestBody UserRequestDTO user) {
//        System.out.println("Received user: " + user);
//        System.out.println("Create user ran");
//        System.out.println(user.toString());

//        return new ResponseEntity<String>("test", HttpStatus.CREATED);
        ComputerUser response = computerUserAccountService.registerComputerUser(user);
        return new ResponseEntity<>(response.getUsername(), HttpStatus.CREATED);
    }

    @GetMapping("/test")
    public String test() {
        return "This is a test";
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody ComputerUserDTO user) {
        System.out.println("IN LOGIN");
        String response = computerUserAccountService.loginUser(user);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/spring")
    public ResponseEntity<String> api() {
        return new ResponseEntity<>("This is a api", HttpStatus.OK);
    }
}
