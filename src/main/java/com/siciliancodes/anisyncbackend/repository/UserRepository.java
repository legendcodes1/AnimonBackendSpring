package com.siciliancodes.anisyncbackend.repository;

import com.siciliancodes.anisyncbackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository  // Good practice to add this annotation
public interface UserRepository extends JpaRepository<User, UUID> {

    // Method names MUST match field names exactly
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);             // Bonus: useful for validation
    boolean existsByUsername(String username);       // Bonus: useful for validation
}