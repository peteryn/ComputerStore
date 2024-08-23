package org.example.computerstore.dto;

public class UserJwtResponseDTO {
    private String jwt;

    public UserJwtResponseDTO(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
