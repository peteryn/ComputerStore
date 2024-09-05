package org.example.computerstore.dto;

public class UserDetailsDTO {
    private String firstName;
    private String lastName;

    public UserDetailsDTO(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

}
