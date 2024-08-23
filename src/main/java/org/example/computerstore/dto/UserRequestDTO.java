package org.example.computerstore.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UserRequestDTO {

//    @Pattern(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$", message = "Username must be a valid email address")

    public UserRequestDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public UserRequestDTO() {}

    @NotNull(message = "Email cannot be null")
    @NotBlank(message = "email is required")
    @Email(message = "Username must be a valid email")
    private String username;

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
