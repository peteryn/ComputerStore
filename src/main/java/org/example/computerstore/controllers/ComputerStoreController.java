package org.example.computerstore.controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.example.computerstore.dto.UserJwtResponseDTO;
import org.example.computerstore.dto.UserCreateDTO;
import org.example.computerstore.dto.UserLoginDTO;
import org.example.computerstore.dto.UserResponseDTO;
import org.example.computerstore.entities.ComputerUser;
import org.example.computerstore.services.ComputerUserAccountService;
import org.example.computerstore.util.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ComputerStoreController {

    private final ComputerUserAccountService computerUserAccountService;
    private final JwtUtil jwtUtil;

    public ComputerStoreController(final ComputerUserAccountService computerUserAccountService, JwtUtil jwtUtil) {
        this.computerUserAccountService = computerUserAccountService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserCreateDTO user) {
        Optional<ComputerUser> response = computerUserAccountService.registerComputerUser(user);
        if (response.isPresent()) {
            return new ResponseEntity<>(new UserResponseDTO(response.get().getUsername()), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(new UserResponseDTO(""), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<UserJwtResponseDTO> login(@RequestBody @Valid UserLoginDTO user, HttpServletResponse response) {
        Optional<String> loginResponse = computerUserAccountService.loginUser(user);
        if (loginResponse.isPresent()) {
            Cookie cookie = new Cookie("JWT", loginResponse.get());
            cookie.setHttpOnly(true);
            cookie.setSecure(true);
            cookie.setMaxAge(5*60);
            response.addCookie(cookie);
            return new ResponseEntity<>(new UserJwtResponseDTO("true"), HttpStatus.OK);
        }
        return new ResponseEntity<>(new UserJwtResponseDTO("false"), HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/protected")
    public ResponseEntity<UserJwtResponseDTO> protectedUser() {
        return new ResponseEntity<>(new UserJwtResponseDTO("Protected Data"), HttpStatus.OK);
    }

    @GetMapping("/check")
    public ResponseEntity<UserJwtResponseDTO> checkUser() {
        return new ResponseEntity<>(new UserJwtResponseDTO("true"), HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<UserJwtResponseDTO> logout(HttpServletResponse response) {
        System.out.println("In logout");
        Cookie cookie = new Cookie("JWT", "");
        cookie.setMaxAge(0);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        response.addCookie(cookie);
        return new ResponseEntity<>(new UserJwtResponseDTO("true"), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public void deleteUser(@CookieValue(value = "JWT", defaultValue = "defaultValue") String myCookie, HttpServletResponse response) {
        if (myCookie != null) {
            String username = jwtUtil.extractUsername(myCookie);
            computerUserAccountService.deleteUser(username);
        }
    }
}
