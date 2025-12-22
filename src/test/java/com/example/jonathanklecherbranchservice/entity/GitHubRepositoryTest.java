package com.example.jonathanklecherbranchservice.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the GitHubRepository entity class.
 * Tests cover:
 * - Default constructor initialization
 * - Individual setter methods for name and url properties
 * - Combined setter operations
 */
class GitHubRepositoryTest {

    private static final String REPO_NAME = "awesome-project";
    private static final String REPO_URL = "https://api.github.com/repos/user/awesome-project";

    private GitHubRepository repository;

    @BeforeEach
    void setUp() {
        repository = new GitHubRepository();
    }

    @Test
    @DisplayName("Default constructor should initialize GitHubRepository object")
    void testDefaultConstructorNotNull() {
        assertNotNull(repository, "GitHubRepository instance should not be null");
    }

    @Test
    @DisplayName("Default constructor should initialize name to null")
    void testDefaultConstructorNameNull() {
        assertNull(repository.getName(), "name should be null after default construction");
    }

    @Test
    @DisplayName("Default constructor should initialize url to null")
    void testDefaultConstructorUrlNull() {
        assertNull(repository.getUrl(), "url should be null after default construction");
    }

    @Test
    @DisplayName("setName() should properly update repository name")
    void testSetName() {
        repository.setName("test-repo");
        assertEquals("test-repo", repository.getName(), "name should be set to 'test-repo'");
    }

    @Test
    @DisplayName("setUrl() should properly update repository URL")
    void testSetUrl() {
        repository.setUrl("https://github.com/user/test-repo");
        assertEquals("https://github.com/user/test-repo", repository.getUrl(), "url should be set to the provided value");
    }

    @Test
    @DisplayName("setName() then getUrl() should preserve previous null url")
    void testSetNameWithoutUrl() {
        repository.setName(REPO_NAME);
        assertEquals(REPO_NAME, repository.getName(), "name should be set correctly");
    }

    @Test
    @DisplayName("setUrl() then getName() should preserve previous null name")
    void testSetUrlWithoutName() {
        repository.setUrl(REPO_URL);
        assertEquals(REPO_URL, repository.getUrl(), "url should be set correctly");
    }

    @Test
    @DisplayName("Setting both name and url should preserve both values")
    void testSetNameAndUrlBothSet() {
        repository.setName(REPO_NAME);
        repository.setUrl(REPO_URL);

        assertEquals(REPO_NAME, repository.getName(), "name should be preserved");
    }

    @Test
    @DisplayName("Setting both name and url should preserve url value")
    void testSetNameAndUrlUrlPreserved() {
        repository.setName(REPO_NAME);
        repository.setUrl(REPO_URL);

        assertEquals(REPO_URL, repository.getUrl(), "url should be preserved");
    }
}

