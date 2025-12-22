package com.example.jonathanklecherbranchservice.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration tests for the GitInfoController class.
 * Tests cover:
 * - Full Spring context loading and application startup
 * - HTTP endpoint behavior with real MockMvc testing
 * - Request validation at the HTTP level
 * - Response status codes for various input scenarios
 */
@SpringBootTest
@AutoConfigureMockMvc
class GitInfoControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private static final String VALID_USER = "octocat";

    @Test
    @DisplayName("GET /users/octocat should successfully fetch valid GitHub user and return HTTP 200 OK")
    @SuppressWarnings("PMD")
    void testGetGitInfoWithValidUser() throws Exception {
        mockMvc.perform(get("/users/" + VALID_USER))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET /users/{username} with non-existent user should return HTTP 404 Not Found")
    @SuppressWarnings("PMD")
    void testGetGitInfoWithInvalidUser() throws Exception {
        mockMvc.perform(get("/users/thisuserdoesnotexist1234567890"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("GET /users/{username} with blank username should return HTTP 400 Bad Request")
    @SuppressWarnings("PMD")
    void testGetGitInfoWithBlankUsername() throws Exception {
        mockMvc.perform(get("/users/ "))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("GET /users/{username} with username exceeding maximum length should return HTTP 400 Bad Request")
    @SuppressWarnings("PMD")
    void testGetGitInfoWithLongUsername() throws Exception {
        final String longUsername = "a".repeat(40);
        mockMvc.perform(get("/users/" + longUsername))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("GET /users/{username} with username starting with dash should return HTTP 400 Bad Request")
    @SuppressWarnings("PMD")
    void testGetGitInfoWithDashAtStart() throws Exception {
        mockMvc.perform(get("/users/-invalid"))
                .andExpect(status().isBadRequest());
    }
}

