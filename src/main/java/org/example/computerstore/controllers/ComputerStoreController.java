package org.example.computerstore.controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.example.computerstore.dto.*;
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
            cookie.setMaxAge(5 * 60);
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
        response.addCookie(createExpiredCookie());
        return new ResponseEntity<>(new UserJwtResponseDTO("true"), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public void deleteUser(@CookieValue(value = "JWT", defaultValue = "defaultValue") String myCookie, @RequestBody UserPasswordDTO userPassword, HttpServletResponse response) {
        String username = jwtUtil.extractUsername(myCookie);
        boolean result = computerUserAccountService.deleteUser(username, userPassword.getPassword());
        if (result) {
            response.setStatus(HttpStatus.NO_CONTENT.value());
            response.addCookie(createExpiredCookie());
        } else {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
        }
    }

    @GetMapping("/details")
    public UserDetailsDTO getDetails(@CookieValue(value = "JWT", defaultValue = "defaultValue") String myCookie) {
        String username = jwtUtil.extractUsername(myCookie);
        Optional<ComputerUser> cu = computerUserAccountService.getUserInfo(username);
        if (cu.isPresent()) {
            return new UserDetailsDTO(cu.get().getFirstName(), cu.get().getLastName());
        } else {
            return new UserDetailsDTO("", "");
        }
    }

    @PutMapping("/update")
    public void updateDetails(@CookieValue(value = "JWT", defaultValue = "defaultValue") String myCookie, @RequestBody UserDetailsDTO userInfo) {
        String username = jwtUtil.extractUsername(myCookie);
        computerUserAccountService.updateUserInfo(username, userInfo.getFirstName(), userInfo.getLastName());
    }

    @PutMapping("/changePassword")
    public void changePassword(@CookieValue(value = "JWT", defaultValue = "defaultValue") String myCookie, @RequestBody UserPasswordUpdateDTO userPasswordUpdateDTO, HttpServletResponse response) {
        String username = jwtUtil.extractUsername(myCookie);
        computerUserAccountService.updatePassword(username, userPasswordUpdateDTO.getOldPassword(), userPasswordUpdateDTO.getNewPassword());
    }

    private Cookie createExpiredCookie() {
        Cookie cookie = new Cookie("JWT", "");
        cookie.setMaxAge(0);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        return cookie;
    }
}
