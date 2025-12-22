package com.example.jonathanklecherbranchservice.service;

import com.example.jonathanklecherbranchservice.entity.GitHubInfo;
import com.example.jonathanklecherbranchservice.entity.GitHubRepository;
import com.example.jonathanklecherbranchservice.mapper.GitHubInfoMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Service for fetching GitHub user and repository information from the GitHub API.
 * <p>
 * This stateless service provides methods to:
 * <ul>
 *   <li>Fetch user profile information by username</li>
 *   <li>Fetch a user's public repositories</li>
 *   <li>Fetch combined user info and repositories in a single call</li>
 * </ul>
 * <p>
 * This service is stateless and does not perform any caching (caching is handled at the
 * controller level). It throws a custom {@link GitHubServiceException} to propagate both
 * error messages and HTTP status codes to the controller, enabling robust error handling.
 * <p>
 * Why Custom Exceptions? A custom exception (vs. built-in or generic exceptions) allows us to:
 * <ul>
 *   <li>Propagate HTTP status codes alongside error messages</li>
 *   <li>Provide context-specific error information</li>
 *   <li>Give controllers flexible error handling capabilities</li>
 * </ul>
 */
@Service
public class GitHubService {

    private static final String API_BASE_URL = "https://api.github.com/users/";
    private static final String REPOS_ENDPOINT = "/repos";

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    // ========== PUBLIC SERVICE METHODS ==========

    /**
     * Fetches GitHub user profile information for the given username.
     * <p>
     * Retrieves user data including name, avatar, location, email, and account creation date.
     * Does not include repository information; use {@link #getGitRepoInfo(String)} or
     * {@link #getGitInfoWithRepos(String)} for repository data.
     *
     * @param userName the GitHub username to fetch
     * @return GitHubInfo entity containing user profile data
     * @throws GitHubServiceException if the user is not found, the API call fails, or the
     *                                 response cannot be parsed
     */
    public GitHubInfo getGitInfo(final String userName) throws GitHubServiceException {
        final String url = API_BASE_URL + userName;
        final String json = getJsonFromUrl(url);

        final JsonNode node;
        try {
            node = objectMapper.readTree(json);
        } catch (final com.fasterxml.jackson.core.JsonProcessingException parseEx) {
            throw new GitHubServiceException("Failed to parse GitHub user info response", HttpStatus.BAD_GATEWAY, parseEx);
        }
        return GitHubInfoMapper.fromJsonNode(node);
    }

    /**
     * Fetches the list of public repositories for the given GitHub username.
     * <p>
     * Retrieves repository metadata including repository names and API URLs.
     * This method only fetches repositories, not user profile data; use
     * {@link #getGitInfo(String)} or {@link #getGitInfoWithRepos(String)} for user data.
     *
     * @param userName the GitHub username whose repositories to fetch
     * @return List of GitHubRepository objects containing repository metadata
     * @throws GitHubServiceException if the user is not found, the API call fails, or the
     *                                 response cannot be parsed
     */
    public List<GitHubRepository> getGitRepoInfo(final String userName) throws GitHubServiceException {
        final String url = API_BASE_URL + userName + REPOS_ENDPOINT;
        final String json = getJsonFromUrl(url);

        final List<GitHubRepository> repos;
        try {
            repos = objectMapper.readValue(json,
                    objectMapper.getTypeFactory().constructCollectionType(List.class, GitHubRepository.class));
        } catch (final com.fasterxml.jackson.core.JsonProcessingException parseEx) {
            throw new GitHubServiceException("Failed to parse GitHub repository list response", HttpStatus.BAD_GATEWAY, parseEx);
        }
        return repos;
    }

    /**
     * Fetches combined GitHub user profile and repositories in a single logical operation.
     * <p>
     * This method calls both {@link #getGitInfo(String)} and {@link #getGitRepoInfo(String)}
     * and combines the results into a single GitHubInfo entity with populated repositories.
     * If either call fails, an exception is thrown immediately.
     *
     * @param userName the GitHub username for which to fetch user info and repositories
     * @return GitHubInfo entity with repositories list populated
     * @throws GitHubServiceException if either the user info or repositories API calls fail
     */
    public GitHubInfo getGitInfoWithRepos(final String userName) throws GitHubServiceException {
        final GitHubInfo info = getGitInfo(userName);
        final List<GitHubRepository> repos = getGitRepoInfo(userName);
        info.setRepositories(repos);
        return info;
    }

    // ========== PRIVATE HELPER METHODS ==========

    /**
     * Fetches JSON data from a specified GitHub API URL.
     * <p>
     * Handles HTTP errors by converting them to GitHubServiceException instances with
     * appropriate HTTP status codes. Provides clear error messages for different scenarios:
     * <ul>
     *   <li>404 Not Found: User does not exist</li>
     *   <li>Other HTTP errors: Generic message with status code</li>
     * </ul>
     *
     * @param url the GitHub API endpoint URL to fetch data from
     * @return the JSON response as a String
     * @throws GitHubServiceException if the HTTP request fails or returns an error status
     */
    private String getJsonFromUrl(final String url) throws GitHubServiceException {
        try {
            return restTemplate.getForObject(url, String.class);
        } catch (final HttpClientErrorException exception) {
            if (exception.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new GitHubServiceException("GitHub user not found", HttpStatus.NOT_FOUND, exception);
            }
            throw new GitHubServiceException("GitHub user not found or error occurred: " + exception.getStatusCode(),
                    HttpStatus.valueOf(exception.getStatusCode().value()), exception);
        }
    }
}

