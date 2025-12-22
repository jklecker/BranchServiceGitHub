package com.example.jonathanklecherbranchservice.mapper;

import com.example.jonathanklecherbranchservice.entity.GitHubInfo;
import com.fasterxml.jackson.databind.JsonNode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Utility class for mapping GitHub API JSON responses to domain entities.
 * <p>
 * This mapper provides transformation logic for converting raw JSON data from the GitHub API
 * into structured {@link GitHubInfo} domain objects. Instead of using {@code @JsonProperty}
 * annotations directly on the entity (which would be simpler but less flexible), we use a
 * dedicated mapper to:
 * <ul>
 *   <li>Apply custom transformations (e.g., date formatting)</li>
 *   <li>Provide flexibility for future mapping changes</li>
 *   <li>Keep mapping logic separate from the domain model</li>
 *   <li>Handle null values gracefully</li>
 * </ul>
 * <p>
 * This is a utility class and should not be instantiated.
 */
public final class GitHubInfoMapper {

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private GitHubInfoMapper() {
    }

    // ========== PUBLIC MAPPING METHODS ==========

    /**
     * Maps a JsonNode representing a GitHub user API response to a GitHubInfo entity.
     * <p>
     * This method extracts relevant fields from the GitHub API response and applies
     * any necessary transformations:
     * <ul>
     *   <li>login → userName</li>
     *   <li>name → displayName</li>
     *   <li>avatar_url → avatar</li>
     *   <li>location → geoLocation</li>
     *   <li>email → email (preserving null values)</li>
     *   <li>url → url</li>
     *   <li>created_at → createdAt (formatted from ISO 8601 to RFC 1123)</li>
     * </ul>
     *
     * @param node the JsonNode containing the GitHub user API response data
     * @return a populated GitHubInfo entity with all fields mapped and transformed appropriately
     * @see #formatCreatedAt(String) for date transformation details
     */
    public static GitHubInfo fromJsonNode(final JsonNode node) {
        final GitHubInfo info = new GitHubInfo();
        info.setUserName(node.path("login").asText(null));
        info.setDisplayName(node.path("name").asText(null));
        info.setAvatar(node.path("avatar_url").asText(null));
        info.setGeoLocation(node.path("location").asText(null));
        info.setEmail(node.path("email").isNull() ? null : node.path("email").asText(null));
        info.setUrl(node.path("url").asText(null));
        info.setCreatedAt(formatCreatedAt(node.path("created_at").asText(null)));
        return info;
    }

    // ========== PRIVATE HELPER METHODS ==========

    /**
     * Converts a date string from ISO 8601 format to RFC 1123 format for HTTP headers.
     * <p>
     * Conversion: "2011-01-25T18:44:36Z" → "Tue, 25 Jan 2011 18:44:36 GMT"
     * <p>
     * If the input is null, blank, or cannot be parsed, returns the original string
     * unchanged. This graceful degradation prevents errors while still providing
     * formatted dates when possible.
     *
     * @param isoDate the date string in ISO 8601 format (e.g., "2011-01-25T18:44:36Z")
     * @return the formatted date string in RFC 1123 format, or the original string if
     *         parsing fails or input is null/blank
     */
    private static String formatCreatedAt(final String isoDate) {
        String result = isoDate;
        if (isoDate != null && !isoDate.isBlank()) {
            try {
                final SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
                isoFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                final Date date = isoFormat.parse(isoDate);

                final SimpleDateFormat targetFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
                targetFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
                result = targetFormat.format(date);
            } catch (final ParseException e) {
                // Gracefully fall back to original string if parsing fails
                result = isoDate;
            }
        }
        return result;
    }
}

