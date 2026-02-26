package com.siciliancodes.anisyncbackend;


import com.siciliancodes.anisyncbackend.dto.request.LoginRequest;
import com.siciliancodes.anisyncbackend.dto.request.RegisterRequest;
import com.siciliancodes.anisyncbackend.dto.response.AuthResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class QuickApiTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldRegisterNewUser() {
        // Given
        RegisterRequest request = new RegisterRequest();
        request.setUsername("testuser" + System.currentTimeMillis()); // Unique username
        request.setEmail("test" + System.currentTimeMillis() + "@example.com"); // Unique email
        request.setPassword("password123");

        // When
        ResponseEntity<AuthResponse> response = restTemplate.postForEntity(
                "/api/auth/register",
                request,
                AuthResponse.class
        );

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getToken()).isNotEmpty();
    }

    @Test
    void shouldLoginExistingUser() {
        // Given - First register a user
        String uniqueEmail = "login" + System.currentTimeMillis() + "@example.com";
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("logintest" + System.currentTimeMillis());
        registerRequest.setEmail(uniqueEmail);
        registerRequest.setPassword("password123");
        restTemplate.postForEntity("/api/auth/register", registerRequest, AuthResponse.class);

        // When - Login with same credentials
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(uniqueEmail);
        loginRequest.setPassword("password123");

        ResponseEntity<AuthResponse> response = restTemplate.postForEntity(
                "/api/auth/login",
                loginRequest,
                AuthResponse.class
        );

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getToken()).isNotEmpty();
    }

    @Test
    void shouldGetAllGroups() {
        // When
        ResponseEntity<String> response = restTemplate.getForEntity(
                "/api/groups",
                String.class
        );

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("["); // It's an array
    }
}