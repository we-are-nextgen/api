package org.nextgen.service;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Map;
import java.util.Optional;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class EducatesAuthService {

    private static final Logger LOG = Logger.getLogger(EducatesAuthService.class);

    @ConfigProperty(name = "educates.base-url")
    String baseUrl;

    @ConfigProperty(name = "educates.oauth.client-id")
    Optional<String> clientId;

    @ConfigProperty(name = "educates.oauth.client-secret")
    Optional<String> clientSecret;

    @ConfigProperty(name = "educates.oauth.username")
    Optional<String> username;

    @ConfigProperty(name = "educates.oauth.password")
    Optional<String> password;

    @Inject
    ObjectMapper objectMapper;

    private final HttpClient httpClient = HttpClient.newHttpClient();

    private volatile String cachedToken;
    private volatile Instant tokenExpiresAt = Instant.EPOCH;

    public String getAccessToken() {
        if (cachedToken != null && Instant.now().isBefore(tokenExpiresAt.minusSeconds(60))) {
            return cachedToken;
        }
        return refreshAccessToken();
    }

    public synchronized String refreshAccessToken() {
        if (clientId.isEmpty() || clientSecret.isEmpty() || username.isEmpty() || password.isEmpty()) {
            throw new IllegalStateException("Educates OAuth credentials are not configured");
        }

        String formBody = "grant_type=password"
                + "&username=" + urlEncode(username.get())
                + "&password=" + urlEncode(password.get());

        String credentials = clientId.get() + ":" + clientSecret.get();
        String basicAuth = java.util.Base64.getEncoder()
                .encodeToString(credentials.getBytes(StandardCharsets.UTF_8));

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(normalizeBaseUrl() + "/oauth2/token/"))
                    .header("Authorization", "Basic " + basicAuth)
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString(formBody))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                LOG.errorf("Educates OAuth failed: HTTP %d - %s", response.statusCode(), response.body());
                throw new RuntimeException("Educates authentication failed: HTTP " + response.statusCode());
            }

            JsonNode json = objectMapper.readTree(response.body());
            cachedToken = json.path("access_token").asText(null);
            if (cachedToken == null || cachedToken.isBlank()) {
                throw new RuntimeException("Educates authentication response missing access_token");
            }

            int expiresIn = json.path("expires_in").asInt(3600);
            tokenExpiresAt = Instant.now().plusSeconds(expiresIn);
            return cachedToken;
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Educates authentication failed", e);
        }
    }

    public void invalidateToken() {
        cachedToken = null;
        tokenExpiresAt = Instant.EPOCH;
    }

    private String normalizeBaseUrl() {
        return baseUrl.endsWith("/") ? baseUrl.substring(0, baseUrl.length() - 1) : baseUrl;
    }

    private static String urlEncode(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }
}
