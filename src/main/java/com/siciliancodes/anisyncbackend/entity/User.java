package com.siciliancodes.anisyncbackend.entity;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "users") // avoids SQL keyword conflict
public class User {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String email;

    // Required by JPA
    protected User() {}

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password; // hash later
        this.email = email;
    }

    // Getters
    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    // Setters (optional but common)
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
