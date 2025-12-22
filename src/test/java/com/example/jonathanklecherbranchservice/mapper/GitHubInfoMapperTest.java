package com.example.jonathanklecherbranchservice.mapper;

import com.example.jonathanklecherbranchservice.entity.GitHubInfo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the GitHubInfoMapper utility class.
 * Tests cover:
 * - Mapping complete JSON responses to GitHubInfo entity objects
 * - Handling null/missing fields gracefully
 * - Field name transformation (snake_case JSON to camelCase Java)
 * - JSON parsing with various data completeness scenarios
 */
class GitHubInfoMapperTest {

    private static final String COMPLETE_JSON = """
            {
              "login": "octocat",
              "name": "The Octocat",
              "avatar_url": "https://github.com/images/error/octocat_happy.gif",
              "location": "San Francisco",
              "email": "octocat@github.com",
              "url": "https://api.github.com/users/octocat",
              "created_at": "2011-01-25T18:44:36Z"
            }
            """;

    private static final String JSON_NULL_EMAIL = """
            {
              "login": "octocat",
              "name": "The Octocat",
              "avatar_url": "https://github.com/images/error/octocat_happy.gif",
              "location": "San Francisco",
              "email": null,
              "url": "https://api.github.com/users/octocat",
              "created_at": "2011-01-25T18:44:36Z"
            }
            """;

    private static final String MINIMAL_JSON = """
            {
              "login": "octocat",
              "name": "The Octocat"
            }
            """;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("fromJsonNode() should map login field to userName")
    void testMapLoginField() throws Exception {
        final JsonNode node = objectMapper.readTree(COMPLETE_JSON);
        final GitHubInfo info = GitHubInfoMapper.fromJsonNode(node);
        assertEquals("octocat", info.getUserName(), "userName should be mapped from login field");
    }

    @Test
    @DisplayName("fromJsonNode() should map name field to displayName")
    void testMapNameField() throws Exception {
        final JsonNode node = objectMapper.readTree(COMPLETE_JSON);
        final GitHubInfo info = GitHubInfoMapper.fromJsonNode(node);
        assertEquals("The Octocat", info.getDisplayName(), "displayName should be mapped from name field");
    }

    @Test
    @DisplayName("fromJsonNode() should map avatar_url field to avatar")
    void testMapAvatarField() throws Exception {
        final JsonNode node = objectMapper.readTree(COMPLETE_JSON);
        final GitHubInfo info = GitHubInfoMapper.fromJsonNode(node);
        assertEquals("https://github.com/images/error/octocat_happy.gif", info.getAvatar(), "avatar should be mapped from avatar_url field");
    }

    @Test
    @DisplayName("fromJsonNode() should map location field to geoLocation")
    void testMapLocationField() throws Exception {
        final JsonNode node = objectMapper.readTree(COMPLETE_JSON);
        final GitHubInfo info = GitHubInfoMapper.fromJsonNode(node);
        assertEquals("San Francisco", info.getGeoLocation(), "geoLocation should be mapped from location field");
    }

    @Test
    @DisplayName("fromJsonNode() should map email field correctly")
    void testMapEmailField() throws Exception {
        final JsonNode node = objectMapper.readTree(COMPLETE_JSON);
        final GitHubInfo info = GitHubInfoMapper.fromJsonNode(node);
        assertEquals("octocat@github.com", info.getEmail(), "email should be mapped correctly");
    }

    @Test
    @DisplayName("fromJsonNode() should map url field correctly")
    void testMapUrlField() throws Exception {
        final JsonNode node = objectMapper.readTree(COMPLETE_JSON);
        final GitHubInfo info = GitHubInfoMapper.fromJsonNode(node);
        assertEquals("https://api.github.com/users/octocat", info.getUrl(), "url should be mapped correctly");
    }

    @Test
    @DisplayName("fromJsonNode() should map created_at field to createdAt")
    void testMapCreatedAtField() throws Exception {
        final JsonNode node = objectMapper.readTree(COMPLETE_JSON);
        final GitHubInfo info = GitHubInfoMapper.fromJsonNode(node);
        assertNotNull(info.getCreatedAt(), "createdAt should be mapped from created_at field");
    }

    @Test
    @DisplayName("fromJsonNode() should handle null email field gracefully")
    void testHandleNullEmail() throws Exception {
        final JsonNode node = objectMapper.readTree(JSON_NULL_EMAIL);
        final GitHubInfo info = GitHubInfoMapper.fromJsonNode(node);
        assertNull(info.getEmail(), "email should be null when JSON field is null");
    }

    @Test
    @DisplayName("fromJsonNode() should map userName when optional fields missing")
    void testMapUserNameWithMissingFields() throws Exception {
        final JsonNode node = objectMapper.readTree(MINIMAL_JSON);
        final GitHubInfo info = GitHubInfoMapper.fromJsonNode(node);
        assertEquals("octocat", info.getUserName(), "userName should be mapped even when other fields are missing");
    }

    @Test
    @DisplayName("fromJsonNode() should map displayName when optional fields missing")
    void testMapDisplayNameWithMissingFields() throws Exception {
        final JsonNode node = objectMapper.readTree(MINIMAL_JSON);
        final GitHubInfo info = GitHubInfoMapper.fromJsonNode(node);
        assertEquals("The Octocat", info.getDisplayName(), "displayName should be mapped even when other fields are missing");
    }

    @Test
    @DisplayName("fromJsonNode() should set avatar to null when field missing")
    void testAvatarNullWhenMissing() throws Exception {
        final JsonNode node = objectMapper.readTree(MINIMAL_JSON);
        final GitHubInfo info = GitHubInfoMapper.fromJsonNode(node);
        assertNull(info.getAvatar(), "avatar should be null when field is missing");
    }

    @Test
    @DisplayName("fromJsonNode() should set geoLocation to null when field missing")
    void testGeoLocationNullWhenMissing() throws Exception {
        final JsonNode node = objectMapper.readTree(MINIMAL_JSON);
        final GitHubInfo info = GitHubInfoMapper.fromJsonNode(node);
        assertNull(info.getGeoLocation(), "geoLocation should be null when field is missing");
    }

    @Test
    @DisplayName("fromJsonNode() should set email to null when field missing")
    void testEmailNullWhenMissing() throws Exception {
        final JsonNode node = objectMapper.readTree(MINIMAL_JSON);
        final GitHubInfo info = GitHubInfoMapper.fromJsonNode(node);
        assertNull(info.getEmail(), "email should be null when field is missing");
    }
}

