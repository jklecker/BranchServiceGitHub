package com.example.jonathanklecherbranchservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot application entry point for the Jonathan Klecker Branch Service.
 * <p>
 * This application is a REST API that integrates with the GitHub API to provide
 * GitHub user profile information and repository details. It includes:
 * <ul>
 *   <li>User profile data retrieval</li>
 *   <li>Repository listing</li>
 *   <li>Input validation</li>
 *   <li>Error handling with custom exceptions</li>
 *   <li>Local caching for resilience</li>
 * </ul>
 * <p>
 * The {@code @SpringBootApplication} annotation enables auto-configuration and
 * component scanning for the application context.
 * <p>
 * Usage: {@code java -jar application.jar} or {@code ./gradlew bootRun}
 *
 * @see com.example.jonathanklecherbranchservice.controller.GitInfoController
 * @see com.example.jonathanklecherbranchservice.service.GitHubService
 */
@SpringBootApplication
@SuppressWarnings("PMD.UseUtilityClass")
public class JonathanKleckerBranchServiceApplication {

    /**
     * Private constructor to prevent instantiation of this utility-like main class.
     */
    private JonathanKleckerBranchServiceApplication() {
    }

    /**
     * Main entry point for the Spring Boot application.
     * <p>
     * Initializes and starts the embedded Tomcat server with Spring context configuration.
     *
     * @param args command-line arguments passed to the application (e.g., --server.port=8081)
     */
    public static void main(final String[] args) {
        SpringApplication.run(JonathanKleckerBranchServiceApplication.class, args);
    }
}
