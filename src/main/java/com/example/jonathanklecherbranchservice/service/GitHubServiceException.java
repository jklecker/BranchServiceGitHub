package com.example.jonathanklecherbranchservice.service;

import org.springframework.http.HttpStatus;
import java.io.Serial;

/**
 * Custom exception for errors encountered when calling the GitHub API.
 * <p>
 * This exception is specifically designed to propagate both a meaningful error message
 * and the relevant HTTP status code from the service layer to the controller. This design
 * choice enables controllers to:
 * <ul>
 *   <li>Respond with the correct HTTP status code</li>
 *   <li>Include appropriate error details in the response body</li>
 *   <li>Handle different error scenarios uniformly</li>
 * </ul>
 * <p>
 * Why use a custom exception instead of built-in Spring exceptions?
 * <ul>
 *   <li>Custom exceptions provide fine-grained control over error propagation</li>
 *   <li>HTTP status codes can be attached and preserved through the call stack</li>
 *   <li>Service layer error semantics are clearer and more explicit</li>
 * </ul>
 * <p>
 * The {@code serialVersionUID} is defined to ensure compatibility during serialization.
 *
 * @see GitHubService
 * @see com.example.jonathanklecherbranchservice.controller.GitInfoController
 */
public class GitHubServiceException extends Exception {

    /**
     * Serial version UID for serialization compatibility.
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * The HTTP status code associated with this exception.
     * <p>
     * This status code should match the appropriate HTTP response status for the error
     * that occurred (e.g., 404 for not found, 500 for server error).
     */
    private final HttpStatus status;

    /**
     * Constructs a new GitHubServiceException with detailed error information.
     * <p>
     * This constructor allows the service layer to specify not only what went wrong
     * (message) and why (cause), but also the appropriate HTTP status code to return
     * to the client.
     *
     * @param message the detail message explaining what went wrong
     * @param status  the HTTP status code to propagate to the HTTP response
     * @param cause   the underlying exception that caused this error (may be null)
     */
    public GitHubServiceException(final String message, final HttpStatus status, final Throwable cause) {
        super(message, cause);
        this.status = status;
    }

    /**
     * Returns the HTTP status code associated with this exception.
     * <p>
     * The controller should use this status code when constructing the HTTP response.
     *
     * @return the {@code HttpStatus} to be used in the response
     */
    public HttpStatus getStatus() {
        return status;
    }
}

