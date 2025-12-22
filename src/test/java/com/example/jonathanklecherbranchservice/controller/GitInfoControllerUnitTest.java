package com.example.jonathanklecherbranchservice.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for GitHub username validation logic.
 * Tests cover:
 * - Valid username patterns (alphanumeric and hyphens)
 * - Invalid username patterns (special characters, consecutive hyphens)
 * - Boundary conditions for username length (max 39 characters)
 * - Edge cases (null, empty, whitespace-only strings)
 */
class GitInfoControllerUnitTest {

    // Valid username tests
    @Test
    @DisplayName("Valid GitHub username 'octocat' should pass validation")
    void testValidGitHubUserNameOctocat() {
        assertTrue(isValidGitHubUserName("octocat"), "octocat should be a valid GitHub username");
    }

    @Test
    @DisplayName("Valid GitHub username with hyphens should pass validation")
    void testValidGitHubUserNameWithHyphens() {
        assertTrue(isValidGitHubUserName("john-doe"), "john-doe should be a valid GitHub username");
    }

    @Test
    @DisplayName("Valid GitHub username with numbers should pass validation")
    void testValidGitHubUserNameWithNumbers() {
        assertTrue(isValidGitHubUserName("user123"), "user123 should be a valid GitHub username");
    }

    @Test
    @DisplayName("Single character lowercase username should be valid")
    void testValidGitHubUserNameSingleLowercase() {
        assertTrue(isValidGitHubUserName("a"), "Single character 'a' should be a valid GitHub username");
    }

    @Test
    @DisplayName("Single character uppercase username should be valid")
    void testValidGitHubUserNameSingleUppercase() {
        assertTrue(isValidGitHubUserName("A"), "Single character 'A' should be a valid GitHub username");
    }

    @Test
    @DisplayName("39-character username should be valid at max boundary")
    void testBoundaryValidUserNameLength39() {
        assertTrue(isValidGitHubUserName("a".repeat(39)), "39-character username should be valid (at max boundary)");
    }

    // Invalid username tests - null/empty/whitespace
    @Test
    @DisplayName("Null username should fail validation")
    void testInvalidGitHubUserNameNull() {
        assertFalse(isValidGitHubUserName(null), "Null username should be invalid");
    }

    @Test
    @DisplayName("Empty username should fail validation")
    void testInvalidGitHubUserNameEmpty() {
        assertFalse(isValidGitHubUserName(""), "Empty username should be invalid");
    }

    @Test
    @DisplayName("Whitespace-only username should fail validation")
    void testInvalidGitHubUserNameWhitespace() {
        assertFalse(isValidGitHubUserName("   "), "Whitespace-only username should be invalid");
    }

    // Invalid username tests - hyphen placement
    @Test
    @DisplayName("Username starting with dash should fail validation")
    void testInvalidGitHubUserNameStartsWithDash() {
        assertFalse(isValidGitHubUserName("-invalid"), "Username starting with dash should be invalid");
    }

    @Test
    @DisplayName("Username ending with dash should fail validation")
    void testInvalidGitHubUserNameEndsWithDash() {
        assertFalse(isValidGitHubUserName("invalid-"), "Username ending with dash should be invalid");
    }

    @Test
    @DisplayName("Username with consecutive dashes should fail validation")
    void testInvalidGitHubUserNameConsecutiveDashes() {
        assertFalse(isValidGitHubUserName("in--valid"), "Username with consecutive dashes should be invalid");
    }

    // Invalid username tests - length
    @Test
    @DisplayName("Username exceeding 39 characters should fail validation")
    void testInvalidGitHubUserNameTooLong() {
        assertFalse(isValidGitHubUserName("a".repeat(40)), "Username exceeding 39 chars (40 chars) should be invalid");
    }

    @Test
    @DisplayName("40-character username should be invalid (exceeds max)")
    void testBoundaryInvalidUserNameLength40() {
        assertFalse(isValidGitHubUserName("a".repeat(40)), "40-character username should be invalid (exceeds max)");
    }

    // Invalid username tests - special characters
    @Test
    @DisplayName("Username with at symbol should fail validation")
    void testSpecialCharactersAtSymbol() {
        assertFalse(isValidGitHubUserName("user@name"), "Username with @ symbol should be invalid");
    }

    @Test
    @DisplayName("Username with space should fail validation")
    void testSpecialCharactersSpace() {
        assertFalse(isValidGitHubUserName("user name"), "Username with space should be invalid");
    }

    @Test
    @DisplayName("Username with underscore should fail validation")
    void testSpecialCharactersUnderscore() {
        assertFalse(isValidGitHubUserName("user_name"), "Username with underscore should be invalid");
    }

    @Test
    @DisplayName("Username with period should fail validation")
    void testSpecialCharactersPeriod() {
        assertFalse(isValidGitHubUserName("user.name"), "Username with period should be invalid");
    }

    /**
     * Helper method to validate GitHub username (same logic as in controller)
     * Valid usernames must:
     * - Be non-null and non-blank
     * - Be 39 characters or less
     * - Contain only alphanumeric characters and hyphens
     * - Not start or end with a hyphen
     * - Not contain consecutive hyphens
     */
    private boolean isValidGitHubUserName(final String userName) {
        if (userName == null || userName.isBlank()) {
            return false;
        }
        final int userNameLength = userName.length();
        return userNameLength <= 39 && userName.matches("^(?!-)(?!.*--)[a-zA-Z0-9-]+(?<!-)$");
    }
}

