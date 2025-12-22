package com.example.jonathanklecherbranchservice.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Domain entity representing GitHub user profile information with associated repositories.
 * <p>
 * This class encapsulates all user-related data retrieved from the GitHub API, including:
 * <ul>
 *   <li>User identification: userName, displayName</li>
 *   <li>User profile: avatar, geoLocation, email, url</li>
 *   <li>Account metadata: createdAt</li>
 *   <li>User repositories: repositories list</li>
 * </ul>
 * <p>
 * The class uses defensive copying for the repositories list to prevent external
 * modification of internal state. This class is a data holder (DTO-like) with no
 * business logic, hence the PMD DataClass warning is suppressed.
 */
@SuppressWarnings("PMD.DataClass")
public class GitHubInfo {

    @JsonProperty("user_name")
    private String userName;

    @JsonProperty("display_name")
    private String displayName;

    @JsonProperty("avatar")
    private String avatar;

    @JsonProperty("geo_location")
    private String geoLocation;

    @JsonProperty("email")
    private String email;

    @JsonProperty("url")
    private String url;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("repositories")
    private List<GitHubRepository> repositories;

    /**
     * Default constructor. Initializes all fields to null.
     */
    public GitHubInfo() {
    }

    /**
     * Constructs a GitHubInfo with all fields initialized.
     *
     * @param userName the GitHub username (login)
     * @param displayName the user's display name
     * @param avatar the URL to the user's avatar image
     * @param geoLocation the user's geographic location (from profile)
     * @param email the user's email address (may be null if not public)
     * @param url the API URL for this user
     * @param createdAt the date the account was created (formatted)
     * @param repositories the list of the user's repositories (will be defensively copied)
     */
    public GitHubInfo(final String userName, final String displayName, final String avatar,
                      final String geoLocation, final String email, final String url,
                      final String createdAt, final List<GitHubRepository> repositories) {
        this.userName = userName;
        this.displayName = displayName;
        this.avatar = avatar;
        this.geoLocation = geoLocation;
        this.email = email;
        this.url = url;
        this.createdAt = createdAt;
        this.repositories = Optional.ofNullable(repositories).map(ArrayList::new).orElse(null);
    }

    // ========== GETTER AND SETTER METHODS ==========

    /**
     * Gets the GitHub username (login).
     *
     * @return the username
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the GitHub username (login).
     *
     * @param userName the username to set
     */
    public void setUserName(final String userName) {
        this.userName = userName;
    }

    /**
     * Gets the user's display name.
     *
     * @return the display name
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Sets the user's display name.
     *
     * @param displayName the display name to set
     */
    public void setDisplayName(final String displayName) {
        this.displayName = displayName;
    }

    /**
     * Gets the URL to the user's avatar image.
     *
     * @return the avatar URL
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     * Sets the URL to the user's avatar image.
     *
     * @param avatar the avatar URL to set
     */
    public void setAvatar(final String avatar) {
        this.avatar = avatar;
    }

    /**
     * Gets the user's geographic location from their profile.
     *
     * @return the location string
     */
    public String getGeoLocation() {
        return geoLocation;
    }

    /**
     * Sets the user's geographic location.
     *
     * @param geoLocation the location to set
     */
    public void setGeoLocation(final String geoLocation) {
        this.geoLocation = geoLocation;
    }

    /**
     * Gets the user's email address.
     *
     * @return the email address (may be null if not publicly visible)
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the user's email address.
     *
     * @param email the email address to set
     */
    public void setEmail(final String email) {
        this.email = email;
    }

    /**
     * Gets the API URL for this user resource.
     *
     * @return the GitHub API URL for this user
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets the API URL for this user resource.
     *
     * @param url the GitHub API URL to set
     */
    public void setUrl(final String url) {
        this.url = url;
    }

    /**
     * Gets the date when this account was created (formatted).
     *
     * @return the creation date in formatted string (e.g., RFC 1123 format)
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets the account creation date.
     *
     * @param createdAt the creation date to set
     */
    public void setCreatedAt(final String createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Gets a defensive copy of the repositories list.
     * <p>
     * Returns a read-only copy to prevent external modification of the internal list.
     *
     * @return an unmodifiable copy of the repositories list, or null if no repositories are set
     */
    public List<GitHubRepository> getRepositories() {
        return Optional.ofNullable(repositories).map(List::copyOf).orElse(null);
    }

    /**
     * Sets the repositories list with defensive copying.
     * <p>
     * Creates an internal copy of the provided list to prevent external modification.
     *
     * @param repositories the repositories list to set (will be defensively copied)
     */
    public void setRepositories(final List<GitHubRepository> repositories) {
        this.repositories = Optional.ofNullable(repositories).map(ArrayList::new).orElse(null);
    }
}
