package com.siciliancodes.anisyncbackend.entity;

import java.util.UUID;

public class User {
    UUID ID;
    private String username;
    private String password;

    public User(String username, String password){
        UUID ID = UUID.randomUUID();
        this.username = username;
        this.password = password;
    }

    public String getUsername(String username){
        return username;
    }

//    public String getPassword(String password){
//        return password;
//    }
}
