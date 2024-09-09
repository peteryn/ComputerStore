package org.example.computerstore.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UserCreateDTO {
    public UserCreateDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @NotBlank(message = "email is required")
    @Email(message = "Username must be a valid email")
    private String username;

    @NotBlank
    @Pattern(message = "Please enter a password with at least 1 uppercase, 1 lowercase, and number",
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$")
    private String password;

    @NotBlank
    @Size(min = 1, max = 32)
    private String firstName;

    @NotBlank
    @Size(min = 1, max = 32)
    public String getLastName() {
        return lastName;
    }

    public @NotBlank @Size(min = 1, max = 32) String getFirstName() {
        return firstName;
    }

    @NotBlank
    @Size(min = 1, max = 32)
    private String lastName;

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
