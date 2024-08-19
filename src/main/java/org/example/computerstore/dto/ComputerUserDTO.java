package org.example.computerstore.dto;

public class ComputerUserDTO {
    private String username;
    private String password;

    public ComputerUserDTO(String password, String username) {
        this.password = password;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
}
