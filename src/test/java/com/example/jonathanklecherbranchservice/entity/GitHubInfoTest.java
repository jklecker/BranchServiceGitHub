package com.example.jonathanklecherbranchservice.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the GitHubInfo entity class.
 * Tests cover:
 * - Default constructor initialization
 * - Parameterized constructor with all fields
 * - Setter methods for all properties
 * - Repositories collection management
 */
class GitHubInfoTest {

    private static final String TEST_USER_NAME = "john";
    private static final String TEST_DISPLAY_NAME = "John Doe";
    private static final String TEST_AVATAR = "avatar.url";
    private static final String TEST_GEO_LOCATION = "San Francisco";
    private static final String TEST_EMAIL = "john@example.com";
    private static final String TEST_URL = "https://github.com/john";
    private static final String TEST_CREATED_AT = "2011-01-25";

    private GitHubInfo gitHubInfo;

    @BeforeEach
    void setUp() {
        gitHubInfo = new GitHubInfo();
    }

    @Test
    @DisplayName("Default constructor should initialize GitHubInfo object")
    void testDefaultConstructorNotNull() {
        assertNotNull(gitHubInfo, "GitHubInfo instance should not be null");
    }

    @Test
    @DisplayName("Default constructor should initialize userName to null")
    void testDefaultConstructorUserNameNull() {
        assertNull(gitHubInfo.getUserName(), "userName should be null after default construction");
    }

    @Test
    @DisplayName("Default constructor should initialize displayName to null")
    void testDefaultConstructorDisplayNameNull() {
        assertNull(gitHubInfo.getDisplayName(), "displayName should be null after default construction");
    }

    @Test
    @DisplayName("Default constructor should initialize avatar to null")
    void testDefaultConstructorAvatarNull() {
        assertNull(gitHubInfo.getAvatar(), "avatar should be null after default construction");
    }

    @Test
    @DisplayName("Default constructor should initialize geoLocation to null")
    void testDefaultConstructorGeoLocationNull() {
        assertNull(gitHubInfo.getGeoLocation(), "geoLocation should be null after default construction");
    }

    @Test
    @DisplayName("Default constructor should initialize email to null")
    void testDefaultConstructorEmailNull() {
        assertNull(gitHubInfo.getEmail(), "email should be null after default construction");
    }

    @Test
    @DisplayName("Default constructor should initialize url to null")
    void testDefaultConstructorUrlNull() {
        assertNull(gitHubInfo.getUrl(), "url should be null after default construction");
    }

    @Test
    @DisplayName("Default constructor should initialize createdAt to null")
    void testDefaultConstructorCreatedAtNull() {
        assertNull(gitHubInfo.getCreatedAt(), "createdAt should be null after default construction");
    }

    @Test
    @DisplayName("Default constructor should initialize repositories to null")
    void testDefaultConstructorRepositoriesNull() {
        assertNull(gitHubInfo.getRepositories(), "repositories should be null after default construction");
    }

    @Test
    @DisplayName("Parameterized constructor should initialize userName correctly")
    void testParameterizedConstructorUserName() {
        final List<GitHubRepository> repos = new ArrayList<>();
        final GitHubInfo info = new GitHubInfo(TEST_USER_NAME, TEST_DISPLAY_NAME, TEST_AVATAR,
                TEST_GEO_LOCATION, TEST_EMAIL, TEST_URL, TEST_CREATED_AT, repos);

        assertEquals(TEST_USER_NAME, info.getUserName(), "userName should be set correctly");
    }

    @Test
    @DisplayName("Parameterized constructor should initialize displayName correctly")
    void testParameterizedConstructorDisplayName() {
        final List<GitHubRepository> repos = new ArrayList<>();
        final GitHubInfo info = new GitHubInfo(TEST_USER_NAME, TEST_DISPLAY_NAME, TEST_AVATAR,
                TEST_GEO_LOCATION, TEST_EMAIL, TEST_URL, TEST_CREATED_AT, repos);

        assertEquals(TEST_DISPLAY_NAME, info.getDisplayName(), "displayName should be set correctly");
    }

    @Test
    @DisplayName("Parameterized constructor should initialize avatar correctly")
    void testParameterizedConstructorAvatar() {
        final List<GitHubRepository> repos = new ArrayList<>();
        final GitHubInfo info = new GitHubInfo(TEST_USER_NAME, TEST_DISPLAY_NAME, TEST_AVATAR,
                TEST_GEO_LOCATION, TEST_EMAIL, TEST_URL, TEST_CREATED_AT, repos);

        assertEquals(TEST_AVATAR, info.getAvatar(), "avatar should be set correctly");
    }

    @Test
    @DisplayName("Parameterized constructor should initialize geoLocation correctly")
    void testParameterizedConstructorGeoLocation() {
        final List<GitHubRepository> repos = new ArrayList<>();
        final GitHubInfo info = new GitHubInfo(TEST_USER_NAME, TEST_DISPLAY_NAME, TEST_AVATAR,
                TEST_GEO_LOCATION, TEST_EMAIL, TEST_URL, TEST_CREATED_AT, repos);

        assertEquals(TEST_GEO_LOCATION, info.getGeoLocation(), "geoLocation should be set correctly");
    }

    @Test
    @DisplayName("Parameterized constructor should initialize email correctly")
    void testParameterizedConstructorEmail() {
        final List<GitHubRepository> repos = new ArrayList<>();
        final GitHubInfo info = new GitHubInfo(TEST_USER_NAME, TEST_DISPLAY_NAME, TEST_AVATAR,
                TEST_GEO_LOCATION, TEST_EMAIL, TEST_URL, TEST_CREATED_AT, repos);

        assertEquals(TEST_EMAIL, info.getEmail(), "email should be set correctly");
    }

    @Test
    @DisplayName("Parameterized constructor should initialize url correctly")
    void testParameterizedConstructorUrl() {
        final List<GitHubRepository> repos = new ArrayList<>();
        final GitHubInfo info = new GitHubInfo(TEST_USER_NAME, TEST_DISPLAY_NAME, TEST_AVATAR,
                TEST_GEO_LOCATION, TEST_EMAIL, TEST_URL, TEST_CREATED_AT, repos);

        assertEquals(TEST_URL, info.getUrl(), "url should be set correctly");
    }

    @Test
    @DisplayName("Parameterized constructor should initialize createdAt correctly")
    void testParameterizedConstructorCreatedAt() {
        final List<GitHubRepository> repos = new ArrayList<>();
        final GitHubInfo info = new GitHubInfo(TEST_USER_NAME, TEST_DISPLAY_NAME, TEST_AVATAR,
                TEST_GEO_LOCATION, TEST_EMAIL, TEST_URL, TEST_CREATED_AT, repos);

        assertEquals(TEST_CREATED_AT, info.getCreatedAt(), "createdAt should be set correctly");
    }

    @Test
    @DisplayName("Parameterized constructor should initialize repositories correctly")
    void testParameterizedConstructorRepositories() {
        final List<GitHubRepository> repos = new ArrayList<>();
        final GitHubInfo info = new GitHubInfo(TEST_USER_NAME, TEST_DISPLAY_NAME, TEST_AVATAR,
                TEST_GEO_LOCATION, TEST_EMAIL, TEST_URL, TEST_CREATED_AT, repos);

        assertEquals(repos, info.getRepositories(), "repositories should be set to the provided list");
    }

    @Test
    @DisplayName("setUserName() should update userName correctly")
    void testSetUserName() {
        gitHubInfo.setUserName(TEST_USER_NAME);
        assertEquals(TEST_USER_NAME, gitHubInfo.getUserName(), "userName should be updated correctly");
    }

    @Test
    @DisplayName("setDisplayName() should update displayName correctly")
    void testSetDisplayName() {
        gitHubInfo.setDisplayName(TEST_DISPLAY_NAME);
        assertEquals(TEST_DISPLAY_NAME, gitHubInfo.getDisplayName(), "displayName should be updated correctly");
    }

    @Test
    @DisplayName("setAvatar() should update avatar correctly")
    void testSetAvatar() {
        gitHubInfo.setAvatar(TEST_AVATAR);
        assertEquals(TEST_AVATAR, gitHubInfo.getAvatar(), "avatar should be updated correctly");
    }

    @Test
    @DisplayName("setGeoLocation() should update geoLocation correctly")
    void testSetGeoLocation() {
        gitHubInfo.setGeoLocation(TEST_GEO_LOCATION);
        assertEquals(TEST_GEO_LOCATION, gitHubInfo.getGeoLocation(), "geoLocation should be updated correctly");
    }

    @Test
    @DisplayName("setEmail() should update email correctly")
    void testSetEmail() {
        gitHubInfo.setEmail(TEST_EMAIL);
        assertEquals(TEST_EMAIL, gitHubInfo.getEmail(), "email should be updated correctly");
    }

    @Test
    @DisplayName("setUrl() should update url correctly")
    void testSetUrl() {
        gitHubInfo.setUrl(TEST_URL);
        assertEquals(TEST_URL, gitHubInfo.getUrl(), "url should be updated correctly");
    }

    @Test
    @DisplayName("setCreatedAt() should update createdAt correctly")
    void testSetCreatedAt() {
        gitHubInfo.setCreatedAt(TEST_CREATED_AT);
        assertEquals(TEST_CREATED_AT, gitHubInfo.getCreatedAt(), "createdAt should be updated correctly");
    }

    @Test
    @DisplayName("setRepositories() should initialize repositories list")
    void testRepositoriesSetterNotNull() {
        final List<GitHubRepository> repos = new ArrayList<>();
        repos.add(new GitHubRepository());
        gitHubInfo.setRepositories(repos);

        assertNotNull(gitHubInfo.getRepositories(), "repositories should not be null after setting");
    }

    @Test
    @DisplayName("setRepositories() should maintain correct repository count")
    void testRepositoriesSetterCount() {
        final List<GitHubRepository> repos = new ArrayList<>();
        repos.add(new GitHubRepository());
        gitHubInfo.setRepositories(repos);

        assertEquals(1, gitHubInfo.getRepositories().size(), "repositories should contain exactly 1 repository");
    }
}

