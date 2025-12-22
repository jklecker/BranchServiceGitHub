package com.example.jonathanklecherbranchservice.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the GitHubServiceException custom exception class.
 * Tests cover:
 * - Constructor initialization with message, status, and cause
 * - Status code getter functionality
 * - Different HTTP status codes
 * - Exception serializability
 */
class GitHubServiceExceptionTest {

    @Test
    @DisplayName("Constructor should initialize message field")
    void testConstructorMessage() {
        final String message = "User not found";
        final HttpStatus status = HttpStatus.NOT_FOUND;
        final Throwable cause = new RuntimeException("Cause");

        final GitHubServiceException exception = new GitHubServiceException(message, status, cause);

        assertEquals(message, exception.getMessage(), "message should be set from constructor parameter");
    }

    @Test
    @DisplayName("Constructor should initialize status field")
    void testConstructorStatus() {
        final String message = "User not found";
        final HttpStatus status = HttpStatus.NOT_FOUND;
        final Throwable cause = new RuntimeException("Cause");

        final GitHubServiceException exception = new GitHubServiceException(message, status, cause);

        assertEquals(status, exception.getStatus(), "status should be set from constructor parameter");
    }

    @Test
    @DisplayName("Constructor should initialize cause field")
    void testConstructorCause() {
        final String message = "User not found";
        final HttpStatus status = HttpStatus.NOT_FOUND;
        final Throwable cause = new RuntimeException("Cause");

        final GitHubServiceException exception = new GitHubServiceException(message, status, cause);

        assertEquals(cause, exception.getCause(), "cause should be set from constructor parameter");
    }

    @Test
    @DisplayName("getStatus() should return HTTP INTERNAL_SERVER_ERROR status")
    void testGetStatusInternalServerError() {
        final GitHubServiceException exception = new GitHubServiceException("Error", HttpStatus.INTERNAL_SERVER_ERROR, null);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus(), "getStatus() should return the initialized status");
    }

    @Test
    @DisplayName("Exception should support NOT_FOUND status code")
    void testWithNotFoundStatus() {
        final GitHubServiceException notFound = new GitHubServiceException("Not found", HttpStatus.NOT_FOUND, null);
        assertEquals(HttpStatus.NOT_FOUND, notFound.getStatus(), "Exception with NOT_FOUND status should return it correctly");
    }

    @Test
    @DisplayName("Exception should support BAD_GATEWAY status code")
    void testWithBadGatewayStatus() {
        final GitHubServiceException badGateway = new GitHubServiceException("Bad gateway", HttpStatus.BAD_GATEWAY, null);
        assertEquals(HttpStatus.BAD_GATEWAY, badGateway.getStatus(), "Exception with BAD_GATEWAY status should return it correctly");
    }

    @Test
    @DisplayName("Exception should support UNAUTHORIZED status code")
    void testWithUnauthorizedStatus() {
        final GitHubServiceException unauthorized = new GitHubServiceException("Unauthorized", HttpStatus.UNAUTHORIZED, null);
        assertEquals(HttpStatus.UNAUTHORIZED, unauthorized.getStatus(), "Exception with UNAUTHORIZED status should return it correctly");
    }

    @Test
    @DisplayName("GitHubServiceException should be serializable")
    void testIsSerializable() {
        final GitHubServiceException exception = new GitHubServiceException("Test", HttpStatus.OK, null);
        assertInstanceOf(java.io.Serializable.class, exception, "GitHubServiceException should implement Serializable");
    }
}

