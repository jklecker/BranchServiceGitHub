package com.example.jonathanklecherbranchservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OpenAPI/Swagger configuration for the GitHub user information API.
 * <p>
 * This configuration defines the API metadata, contact information, and server details
 * for automatic Swagger/OpenAPI documentation generation. The documentation is accessible
 * at `/swagger-ui.html` after the application starts.
 */
@Configuration
public class OpenAPIConfig {

    /**
     * Configures OpenAPI documentation with API metadata and server information.
     * <p>
     * This bean defines:
     * <ul>
     *   <li>API title, version, and description</li>
     *   <li>Contact information for API support</li>
     *   <li>License information</li>
     *   <li>Server URLs for local development and production</li>
     * </ul>
     *
     * @return configured OpenAPI instance
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("GitHub User Information API")
                        .version("0.0.1-SNAPSHOT")
                        .description("REST API for retrieving GitHub user profile information and repositories.\n\n" +
                                "This API provides easy access to GitHub user data including profiles and repository lists. " +
                                "It includes built-in caching for resilience when GitHub API is unavailable.")
                        .contact(new Contact()
                                .name("Jonathan Klecker")
                                .email("jonathan@klecker.dev")
                                .url("https://github.com/jklecker"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .addServersItem(new Server()
                        .url("http://localhost:8080")
                        .description("Local development server"))
                .addServersItem(new Server()
                        .url("https://api.example.com")
                        .description("Production server"));
    }
}

