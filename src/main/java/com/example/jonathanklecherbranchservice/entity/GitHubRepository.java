package com.example.jonathanklecherbranchservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Domain entity representing a GitHub repository with basic information.
 * <p>
 * This class holds minimal repository data retrieved from the GitHub API:
 * <ul>
 *   <li>name: The repository name</li>
 *   <li>url: The API URL for this repository</li>
 * </ul>
 * <p>
 * The {@code @JsonIgnoreProperties} annotation allows this class to be deserialized from
 * GitHub API responses that contain additional fields we don't need, making the class
 * resilient to API changes. This class is a data holder (DTO-like) with no business
 * logic, hence the PMD DataClass warning is suppressed.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@SuppressWarnings("PMD.DataClass")
public class GitHubRepository {

    @JsonProperty("name")
    private String name;

    @JsonProperty("url")
    private String url;

    /**
     * Default constructor. Initializes both fields to null.
     * <p>
     * This constructor is required by Jackson for JSON deserialization.
     */
    public GitHubRepository() {
    }

    /**
     * Constructs a GitHubRepository with both fields initialized.
     * <p>
     * While this constructor is not currently used by Jackson (which uses the default constructor),
     * it is provided for convenience in programmatic creation of repository instances.
     *
     * @param name the repository name
     * @param url the API URL for this repository
     */
    @SuppressWarnings("unused")
    public GitHubRepository(final String name, final String url) {
        this.name = name;
        this.url = url;
    }

    // ========== GETTER AND SETTER METHODS ==========

    /**
     * Gets the repository name.
     *
     * @return the repository name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the repository name.
     *
     * @param name the repository name to set
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Gets the API URL for this repository resource.
     *
     * @return the GitHub API URL for this repository
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets the API URL for this repository resource.
     *
     * @param url the GitHub API URL to set
     */
    public void setUrl(final String url) {
        this.url = url;
    }
}
