package org.nextgen.service;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;
import org.nextgen.dto.WorkshopCatalogEntryDTO;
import org.nextgen.dto.WorkshopSessionResponseDTO;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class EducatesWorkshopService {

    private static final Logger LOG = Logger.getLogger(EducatesWorkshopService.class);

    @ConfigProperty(name = "educates.base-url")
    String baseUrl;

    @ConfigProperty(name = "educates.portal.index-url")
    String portalIndexUrl;

    @Inject
    EducatesAuthService authService;

    @Inject
    ObjectMapper objectMapper;

    private final HttpClient httpClient = HttpClient.newHttpClient();

    public List<WorkshopCatalogEntryDTO> listWorkshops() {
        JsonNode catalog = getJson("/workshops/catalog/environments/", true);
        JsonNode environments = catalog.path("environments");
        List<WorkshopCatalogEntryDTO.EnvironmentEntry> envList = objectMapper.convertValue(
                environments,
                new TypeReference<List<WorkshopCatalogEntryDTO.EnvironmentEntry>>() {}
        );
        return WorkshopCatalogEntryDTO.fromEnvironments(envList);
    }

    public WorkshopSessionResponseDTO requestSession(
            String workshopName,
            String userEmail,
            String returnPath,
            String firstName,
            String lastName
    ) {
        String environmentName = resolveEnvironmentName(workshopName);
        String indexUrl = buildIndexUrl(returnPath);

        String query = "index_url=" + urlEncode(indexUrl)
                + "&user=" + urlEncode(userEmail)
                + "&email=" + urlEncode(userEmail);
        if (firstName != null && !firstName.isBlank()) {
            query += "&first_name=" + urlEncode(firstName);
        }
        if (lastName != null && !lastName.isBlank()) {
            query += "&last_name=" + urlEncode(lastName);
        }

        String path = "/workshops/environment/" + urlEncodePath(environmentName) + "/request/?" + query;
        JsonNode session = getJson(path, true);

        WorkshopSessionResponseDTO response = new WorkshopSessionResponseDTO();
        response.sessionName = textOrNull(session, "name");
        response.workshopName = textOrNull(session, "workshop");
        response.environmentName = textOrNull(session, "environment");
        response.userId = textOrNull(session, "user");

        String relativeUrl = textOrNull(session, "url");
        if (relativeUrl == null) {
            throw new RuntimeException("Educates session response missing activation URL");
        }
        response.sessionActivationUrl = relativeUrl.startsWith("http")
                ? relativeUrl
                : normalizeBaseUrl() + relativeUrl;

        return response;
    }

    public List<WorkshopSessionResponseDTO> listActiveSessions(String userEmail) {
        JsonNode sessionsJson = getJson("/workshops/user/" + urlEncodePath(userEmail) + "/sessions/", true);
        JsonNode sessions = sessionsJson.path("sessions");
        List<WorkshopSessionResponseDTO> result = new ArrayList<>();
        if (!sessions.isArray()) {
            return result;
        }
        for (JsonNode session : sessions) {
            WorkshopSessionResponseDTO dto = new WorkshopSessionResponseDTO();
            dto.sessionName = textOrNull(session, "name");
            dto.workshopName = textOrNull(session, "workshop");
            dto.environmentName = textOrNull(session, "environment");
            result.add(dto);
        }
        return result;
    }

    private String resolveEnvironmentName(String workshopName) {
        JsonNode catalog = getJson("/workshops/catalog/environments/", true);
        JsonNode environments = catalog.path("environments");
        if (!environments.isArray()) {
            throw new RuntimeException("No Educates workshop environments available");
        }

        for (JsonNode env : environments) {
            String state = env.path("state").asText("");
            if (!"RUNNING".equals(state)) {
                continue;
            }
            String envWorkshopName = env.path("workshop").path("name").asText(null);
            if (workshopName.equals(envWorkshopName)) {
                String envName = env.path("name").asText(null);
                if (envName != null) {
                    return envName;
                }
            }
        }

        throw new RuntimeException("No running Educates environment found for workshop: " + workshopName);
    }

    private String buildIndexUrl(String returnPath) {
        String base = portalIndexUrl.endsWith("/")
                ? portalIndexUrl.substring(0, portalIndexUrl.length() - 1)
                : portalIndexUrl;
        String target = (returnPath == null || returnPath.isBlank()) ? "/dashboard/it-professional" : returnPath;
        if (!target.startsWith("/")) {
            target = "/" + target;
        }
        return base + "/workshops/return?target=" + urlEncode(target);
    }

    private JsonNode getJson(String path, boolean retryOnUnauthorized) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(normalizeBaseUrl() + path))
                    .header("Authorization", "Bearer " + authService.getAccessToken())
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 401 && retryOnUnauthorized) {
                authService.invalidateToken();
                return getJson(path, false);
            }

            if (response.statusCode() == 503) {
                throw new RuntimeException("Educates has no capacity available for workshop sessions");
            }

            if (response.statusCode() != 200) {
                LOG.errorf("Educates API error: HTTP %d %s - %s", response.statusCode(), path, response.body());
                throw new RuntimeException("Educates API request failed: HTTP " + response.statusCode());
            }

            return objectMapper.readTree(response.body());
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Educates API request failed", e);
        }
    }

    private String normalizeBaseUrl() {
        return baseUrl.endsWith("/") ? baseUrl.substring(0, baseUrl.length() - 1) : baseUrl;
    }

    private static String textOrNull(JsonNode node, String field) {
        JsonNode value = node.path(field);
        if (value.isMissingNode() || value.isNull()) {
            return null;
        }
        String text = value.asText(null);
        return (text == null || text.isBlank()) ? null : text;
    }

    private static String urlEncode(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }

    private static String urlEncodePath(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8).replace("+", "%20");
    }
}
