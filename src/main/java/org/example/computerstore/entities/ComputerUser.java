package org.example.computerstore.entities;

import jakarta.persistence.*;

@Entity
public class ComputerUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(unique=true)
    private String username;
    private String password;

    protected ComputerUser() {}

    public ComputerUser(String username, String password) {
        this.username = username;
        this.password = password;
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

    @Override
    public String toString() {
        return "ComputerUser [id=" + id + ", username=" + username + ", password=" + password + "]";
    }
}
