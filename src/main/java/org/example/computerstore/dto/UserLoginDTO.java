package org.example.computerstore.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class UserLoginDTO {
    @NotBlank(message = "email is required")
    @Email(message = "Username must be a valid email")
    private String username;

    @NotBlank
    @Pattern(message = "Please enter a password with at least 1 uppercase, 1 lowercase, and number",
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$")
    private String password;

    public @NotBlank(message = "email is required") @Email(message = "Username must be a valid email") String getUsername() {
        return username;
    }

    public @NotBlank @Pattern(message = "Please enter a password with at least 1 uppercase, 1 lowercase, and number",
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$") String getPassword() {
        return password;
    }
}
