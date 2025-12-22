package com.example.jonathanklecherbranchservice.service;

import com.example.jonathanklecherbranchservice.entity.GitHubInfo;
import com.example.jonathanklecherbranchservice.entity.GitHubRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for the GitHubService class.
 * Tests cover:
 * - Fetching GitHub user information via GitHub API
 * - Fetching GitHub repositories for a user
 * - Combining user info with repository data
 * - Error handling when users don't exist
 * - HTTP status code correctness in exceptions
 */
@SpringBootTest
@ActiveProfiles("test")
class GitHubServiceTest {

    private static final String VALID_USER = "octocat";

    @Autowired
    private GitHubService gitHubService;

    @Test
    @DisplayName("getGitInfo() with valid username should fetch user object")
    void testGetGitInfoWithValidUserNotNull() throws GitHubServiceException {
        final GitHubInfo info = gitHubService.getGitInfo(VALID_USER);
        assertNotNull(info, "getGitInfo should return non-null GitHubInfo for valid user");
    }

    @Test
    @DisplayName("getGitInfo() with valid username should fetch correct userName")
    void testGetGitInfoWithValidUserName() throws GitHubServiceException {
        final GitHubInfo info = gitHubService.getGitInfo(VALID_USER);
        assertEquals(VALID_USER, info.getUserName(), "getGitInfo should return correct userName");
    }

    @Test
    @DisplayName("getGitInfo() with valid username should fetch non-null displayName")
    void testGetGitInfoWithValidUserDisplayName() throws GitHubServiceException {
        final GitHubInfo info = gitHubService.getGitInfo(VALID_USER);
        assertNotNull(info.getDisplayName(), "getGitInfo should return non-null displayName");
    }

    @Test
    @DisplayName("getGitInfo() with non-existent username should throw GitHubServiceException")
    void testGetGitInfoWithInvalidUser() {
        assertThrows(GitHubServiceException.class,
                () -> gitHubService.getGitInfo("invalid_user_12345_that_does_not_exist"),
                "getGitInfo should throw exception for non-existent user");
    }

    @Test
    @DisplayName("getGitRepoInfo() with valid username should fetch repositories list")
    void testGetGitRepoInfoWithValidUserNotNull() throws GitHubServiceException {
        final List<GitHubRepository> repos = gitHubService.getGitRepoInfo(VALID_USER);
        assertNotNull(repos, "getGitRepoInfo should return non-null list for valid user");
    }

    @Test
    @DisplayName("getGitRepoInfo() with valid username should return non-empty list")
    void testGetGitRepoInfoWithValidUserNotEmpty() throws GitHubServiceException {
        final List<GitHubRepository> repos = gitHubService.getGitRepoInfo(VALID_USER);
        assertFalse(repos.isEmpty(), "getGitRepoInfo should return non-empty list for valid user");
    }

    @Test
    @DisplayName("getGitRepoInfo() with non-existent username should throw GitHubServiceException")
    void testGetGitRepoInfoWithInvalidUser() {
        assertThrows(GitHubServiceException.class,
                () -> gitHubService.getGitRepoInfo("invalid_user_12345_that_does_not_exist"),
                "getGitRepoInfo should throw exception for non-existent user");
    }

    @Test
    @DisplayName("getGitInfoWithRepos() should return non-null GitHubInfo")
    void testGetGitInfoWithReposNotNull() throws GitHubServiceException {
        final GitHubInfo info = gitHubService.getGitInfoWithRepos(VALID_USER);
        assertNotNull(info, "getGitInfoWithRepos should return non-null GitHubInfo");
    }

    @Test
    @DisplayName("getGitInfoWithRepos() should return correct userName")
    void testGetGitInfoWithReposUserName() throws GitHubServiceException {
        final GitHubInfo info = gitHubService.getGitInfoWithRepos(VALID_USER);
        assertEquals(VALID_USER, info.getUserName(), "getGitInfoWithRepos should return correct userName");
    }

    @Test
    @DisplayName("getGitInfoWithRepos() should include non-null repositories list")
    void testGetGitInfoWithReposHasRepositories() throws GitHubServiceException {
        final GitHubInfo info = gitHubService.getGitInfoWithRepos(VALID_USER);
        assertNotNull(info.getRepositories(), "getGitInfoWithRepos should include repositories list");
    }

    @Test
    @DisplayName("GitHubServiceException should be thrown when user doesn't exist")
    void testExceptionIsThrownForNonExistentUser() {
        assertThrows(GitHubServiceException.class,
                () -> gitHubService.getGitInfo("invalid_user_12345"),
                "Exception should be thrown for non-existent user");
    }

    @Test
    @DisplayName("GitHubServiceException should contain HTTP 404 Not Found status")
    @SuppressWarnings("PMD")
    void testExceptionStatusIs404NotFound() {
        final GitHubServiceException exception = assertThrows(GitHubServiceException.class,
                () -> gitHubService.getGitInfo("invalid_user_12345_does_not_exist"));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus(), "Exception should contain HTTP 404 Not Found status");
    }
}

