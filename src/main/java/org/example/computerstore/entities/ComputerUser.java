package org.example.computerstore.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

@Entity
public class ComputerUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(unique=true)
    private String username;
    private String password;

    private String firstName;
    private String lastName;

    protected ComputerUser() {}

    public ComputerUser(String username, String password, String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public long getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setFirstName(String firstName) {this.firstName = firstName;}

    public void setLastName(String lastName) {this.lastName = lastName;}

    @Override
    public String toString() {
        return "ComputerUser [id=" + id + ", username=" + username + ", password=" + password + "]";
    }
}
