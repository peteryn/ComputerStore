package org.example.computerstore.dto;

public class UserResponseDTO {
    private String username;

    public UserResponseDTO(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
