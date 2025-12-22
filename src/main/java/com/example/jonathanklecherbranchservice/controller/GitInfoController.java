package com.example.jonathanklecherbranchservice.controller;

import com.example.jonathanklecherbranchservice.entity.GitHubInfo;
import com.example.jonathanklecherbranchservice.service.GitHubService;
import com.example.jonathanklecherbranchservice.service.GitHubServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * REST controller for GitHub user information endpoints.
 * <p>
 * This controller handles HTTP requests to retrieve GitHub user profile information and repositories.
 * It validates usernames before making API calls and maintains a local cache to provide cached results
 * when the GitHub API is unavailable. The cache is thread-safe using {@link ConcurrentHashMap}.
 * <p>
 * Endpoint: GET /users/{userName} - Retrieve GitHub user profile and repositories
 */
@RestController
@RequestMapping("/users")
public class GitInfoController {

    private static final String ERROR = "error";
    private static final String STATUS = "status";
    private static final String CACHED = "cached";

    @Autowired
    private GitHubService gitHubService;

    private final Map<String, GitHubInfo> cache = new ConcurrentHashMap<>();

    // ========== PUBLIC METHODS ==========

    /**
     * Retrieves GitHub user information and repositories by username.
     * <p>
     * This endpoint performs the following:
     * 1. Validates the username format against GitHub username rules
     * 2. Fetches user data and repositories from GitHub API (via GitHubService)
     * 3. Returns cached data if available and the API call fails
     * 4. Returns appropriate HTTP status codes based on the result
     *
     * @param userName the GitHub username to look up (must be 1-39 chars, alphanumeric + hyphens)
     * @return {@code ResponseEntity} with GitHubInfo on success (200 OK),
     *         cached data if available (with error and 404),
     *         or error message if invalid username (400 Bad Request)
     */
    @GetMapping("/{userName}")
    public ResponseEntity<?> getGitInfo(@PathVariable final String userName) {
        final ResponseEntity<?> result;
        if (isValidGitHubUserName(userName)) {
            result = processValidUserName(userName);
        } else {
            result = ResponseEntity.badRequest().body(Map.of(ERROR, "Invalid GitHub username"));
        }
        return result;
    }

    // ========== PRIVATE HELPER METHODS ==========

    /**
     * Processes a valid GitHub username by fetching user data from the service.
     * <p>
     * Handles both successful API calls and exceptions:
     * - Success: Caches the data and returns 200 OK
     * - NOT_FOUND (404): Returns cached data if available, otherwise returns 404 error
     * - Other errors: Returns cached data if available with error message and appropriate status
     *
     * @param userName the validated GitHub username
     * @return {@code ResponseEntity} with appropriate status and body
     */
    private ResponseEntity<?> processValidUserName(final String userName) {
        ResponseEntity<?> result;
        try {
            final GitHubInfo info = gitHubService.getGitInfoWithRepos(userName);
            cache.put(userName, info);
            result = ResponseEntity.ok(info);
        } catch (final GitHubServiceException exception) {
            if (exception.getStatus() == HttpStatus.NOT_FOUND) {
                result = handleNotFound();
            } else {
                result = handleServiceException(userName, exception);
            }
        }
        return result;
    }

    /**
     * Handles 404 NOT_FOUND responses from the GitHub API.
     * <p>
     * When a user is not found, returns a standard 404 error response.
     *
     * @return {@code ResponseEntity} with 404 status and error details
     */
    private ResponseEntity<?> handleNotFound() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Map.of(
                        ERROR, "GitHub user not found",
                        STATUS, 404,
                        CACHED, false
                )
        );
    }

    /**
     * Handles unexpected service exceptions by returning cached data if available.
     * <p>
     * If cached data exists for the user, includes it in the response along with
     * the error message and HTTP status. If no cached data exists, returns only
     * the error details with the appropriate status code.
     *
     * @param userName the GitHub username
     * @param exception the caught {@code GitHubServiceException}
     * @return {@code ResponseEntity} with cached data (if available) and error details
     */
    private ResponseEntity<?> handleServiceException(final String userName, final GitHubServiceException exception) {
        final GitHubInfo cached = cache.get(userName);
        if (cached != null) {
            return ResponseEntity.status(exception.getStatus()).body(
                    Map.of(
                            "data", cached,
                            ERROR, exception.getMessage(),
                            STATUS, exception.getStatus().value(),
                            CACHED, true
                    )
            );
        }
        return ResponseEntity.status(exception.getStatus()).body(
                Map.of(
                        ERROR, exception.getMessage(),
                        STATUS, exception.getStatus().value(),
                        CACHED, false
                )
        );
    }

    /**
     * Validates a GitHub username according to GitHub's rules.
     * <p>
     * Valid usernames must:
     * <ul>
     *   <li>Be 1-39 characters long</li>
     *   <li>Start and end with an alphanumeric character (not a hyphen)</li>
     *   <li>Contain only alphanumeric characters and hyphens</li>
     *   <li>Not have consecutive hyphens (--)</li>
     * </ul>
     *
     * @param userName the username to validate
     * @return {@code true} if the username is valid, {@code false} otherwise
     */
    private static boolean isValidGitHubUserName(final String userName) {
        if (userName == null || userName.isBlank()) {
            return false;
        }
        final int userNameLength = userName.length();
        return userNameLength <= 39 && userName.matches("^(?!-)(?!.*--)[a-zA-Z0-9-]+(?<!-)$");
    }
}
