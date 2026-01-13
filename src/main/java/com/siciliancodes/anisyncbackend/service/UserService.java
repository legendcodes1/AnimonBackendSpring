package com.siciliancodes.anisyncbackend.service;

import com.siciliancodes.anisyncbackend.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


public interface UserService {

    User createUser(User user);

    User getUserById(UUID id);

    List<User> getAllUsers();

    void deleteUser(UUID id);
}
