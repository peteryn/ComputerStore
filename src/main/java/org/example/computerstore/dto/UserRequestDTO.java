package org.example.computerstore.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class UserRequestDTO {
    public UserRequestDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @NotBlank(message = "email is required")
    @Email(message = "Username must be a valid email", regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$")
    private String username;

    @NotBlank
    @Pattern(message = "Please enter a password with at least 1 uppercase, 1 lowercase, and number",
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$")
    private String password;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "username: " + username + ", password: " + password;
    }
}
