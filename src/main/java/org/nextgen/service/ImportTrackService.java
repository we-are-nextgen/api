package org.nextgen.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.eclipse.jgit.api.Git;
import org.yaml.snakeyaml.Yaml;
import org.nextgen.dto.ImportTrackDTO;
import org.nextgen.dto.importer.TrackYamlDTO;
import org.nextgen.model.Asset;
import org.nextgen.model.Domain;
import org.nextgen.model.Exercise;
import org.nextgen.model.Lab;
import org.nextgen.model.LearningTrack;

import java.util.List;
import java.io.File;
import java.nio.file.StandardCopyOption;
import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.nio.file.Files;
import java.nio.file.Path;

import org.jboss.logging.Logger;

@ApplicationScoped
public class ImportTrackService {

    private static final String TRACK_YAML = "track.yaml";
    private static final Logger LOG = Logger.getLogger(ImportTrackService.class);

    // Base storage directory for imported assets (can be configured)
    // Example: set to /var/lib/nextgen/assets in prod via config
    private final Path assetBaseDir = Paths.get(System.getProperty("nextgen.assets.path", "/tmp/nextgen-assets"));

    public ImportTrackService() {
        try {
            Files.createDirectories(assetBaseDir);
        } catch (IOException e) {
            LOG.error("Unable to create asset storage dir: " + assetBaseDir, e);
        }
    }

    @Transactional
    public LearningTrack importTrack(ImportTrackDTO dto) throws Exception {
        // 1. Clone repository
        Path cloneDir = Files.createTempDirectory("track-import-");
        Git git = Git.cloneRepository()
                .setURI(dto.gitRepoUrl)
                .setDirectory(cloneDir.toFile())
                .setBranch(dto.gitBranch)
                .call();

        // 2. Resolve gitPath inside repo
        File pathInsideRepo = dto.gitPath == null || dto.gitPath.isBlank() ?
                cloneDir.toFile() :
                cloneDir.resolve(dto.gitPath).toFile();

        // 3. Find track.yaml
        File trackYamlFile = new File(pathInsideRepo, TRACK_YAML);
        if (!trackYamlFile.exists()) {
            throw new RuntimeException("track.yaml not found at: " + trackYamlFile.getPath());
        }

        // 4. Parse YAML
        Yaml yaml = new Yaml();
        TrackYamlDTO yamlDTO = yaml.loadAs(Files.newInputStream(trackYamlFile.toPath()), TrackYamlDTO.class);

        // 5. Validate YAML
        validateYaml(yamlDTO);

        // 6. Store LearningTrack in DB
        LearningTrack track = saveLearningTrack(yamlDTO, dto);

        // 7. Process labs + assets + exercises
        Path labsPath = cloneDir.resolve(dto.gitPath == null ? "" : dto.gitPath);
        System.out.println("Repo URL: ======== " + dto.gitRepoUrl); // --- IGNORE ---
        saveLabsFromRepo(yamlDTO, labsPath, track);

        return track;
    }

    private void validateYaml(TrackYamlDTO dto) {
        if (dto.name == null || dto.name.isBlank()) {
            throw new RuntimeException("Track name is required in track.yaml");
        }
        if (dto.labs == null || dto.labs.isEmpty()) {
            throw new RuntimeException("Track must contain at least one lab");
        }
    }

    /**
     * Persist track metadata (LearningTrack) derived from YAML.
     * Returns the persisted LearningTrack.
     */
    private LearningTrack saveLearningTrack(TrackYamlDTO yamlDTO, ImportTrackDTO dto) {
        // 1. find or create domain
        Domain domainEntity = null;
        if (yamlDTO.domainName != null && !yamlDTO.domainName.isBlank()) {
            domainEntity = Domain.find("name", yamlDTO.domainName).firstResult();
            if (domainEntity == null) {
                domainEntity = new Domain();
                domainEntity.name = yamlDTO.domainName;
                domainEntity.description = yamlDTO.description != null ? yamlDTO.description : null;
                domainEntity.icon = yamlDTO.icon;
                domainEntity.persist();
                LOG.info("Created new domain: " + yamlDTO.domainName);
            } else
                LOG.info("Found domain: " + yamlDTO.domainName);
        }

        // 2. create track entity
        LearningTrack track = new LearningTrack();
        track.name = yamlDTO.name;
        track.uuid = yamlDTO.UUID;
        track.description = yamlDTO.description;
        track.icon = yamlDTO.icon;
        track.difficultyLevel = parseDifficulty(yamlDTO.difficultyLevel);
        track.estimatedTimeMin = yamlDTO.estimatedTimeMin;
        track.rewardPoints = yamlDTO.rewardPoints;

        track.repoUrl = dto.gitRepoUrl;
        track.repoBranch = dto.gitBranch;
        track.repoPath = dto.gitPath;
        track.domain = domainEntity;

        // Persist
        track.persist();
        //LOG.infof("Persisted track '%s' (id=%d)", track.name, track.id);

        return track;
    }

    /**
     * Read labs described in YAML (TrackYamlDTO) and save them.
     * repoRoot is the root path of the cloned repo.
     * track is the persisted LearningTrack.
     */
    private void saveLabsFromRepo(TrackYamlDTO yaml, Path repoRoot, LearningTrack track) throws IOException {
        if (yaml.labs == null || yaml.labs.isEmpty()) {
            LOG.warn("No labs to import for track " + track.name);
            return;
        }

        // create per-track storage dir
        Path trackStorage = assetBaseDir.resolve("track-" + track.id + "-" + sanitizeForPath(track.name));
        Files.createDirectories(trackStorage);

        List<Lab> persistedLabs = new ArrayList<>();

        int order = 0;
        for (TrackYamlDTO.LabYamlDTO l : yaml.labs) {
            order++;
            Lab lab = new Lab();
            lab.name = l.name;
            lab.uuid = l.UUID;
            lab.description = l.description;
            lab.icon = l.icon;
            lab.difficultyLevel = parseDifficulty(l.difficultyLevel != null ? l.difficultyLevel : yaml.difficultyLevel);
            lab.estimatedTimeMin = l.estimatedTimeMin;
            lab.hasBonusTasks = l.hasBonusTasks;
            lab.sequence = l.sequence != null ? l.sequence : order;

            // handle markdown/html content (path or inline)
            if (l.contentMarkdown != null && !l.contentMarkdown.isBlank()) {
                if (looksLikePath(l.contentMarkdown)) {
                    Path mdPath = repoRoot.resolve(l.contentMarkdown).normalize();
                    if (Files.exists(mdPath)) {
                        String dest = saveFileToStorage(mdPath, trackStorage, "lab-" + order + "-content.md");
                        lab.contentMarkdownUrl = dest;
                        LOG.info("Saved contentMarkdown for lab '" + lab.name + "' -> " + dest);
                    } else {
                        LOG.warn("contentMarkdown path not found: " + mdPath);
                    }
                } else {
                    Path tmp = Files.createTempFile("lab-content-", ".md");
                    Files.writeString(tmp, l.contentMarkdown, StandardOpenOption.TRUNCATE_EXISTING);
                    String dest = saveFileToStorage(tmp, trackStorage, "lab-" + order + "-content.md");
                    lab.contentMarkdownUrl = dest;
                    Files.deleteIfExists(tmp);
                }
            }

            if (l.contentHtml != null && !l.contentHtml.isBlank()) {
                if (looksLikePath(l.contentHtml)) {
                    Path htmlPath = repoRoot.resolve(l.contentHtml).normalize();
                    if (Files.exists(htmlPath)) {
                        String dest = saveFileToStorage(htmlPath, trackStorage, "lab-" + order + "-content.html");
                        lab.contentHtmlUrl = dest;
                        LOG.info("Saved contentHtml for lab '" + lab.name + "' -> " + dest);
                    } else {
                        LOG.warn("contentHtml path not found: " + htmlPath);
                    }
                } else {
                    Path tmp = Files.createTempFile("lab-content-", ".html");
                    Files.writeString(tmp, l.contentHtml, StandardOpenOption.TRUNCATE_EXISTING);
                    String dest = saveFileToStorage(tmp, trackStorage, "lab-" + order + "-content.html");
                    lab.contentHtmlUrl = dest;
                    Files.deleteIfExists(tmp);
                }
            }

            // Persist lab first to get its id
            lab.persist();
            //LOG.infof("Persisted lab '%s' (id=%d)", lab.name, lab.id);

            // assets
            if (l.assets != null && !l.assets.isEmpty()) {
                List<Asset> savedAssets = new ArrayList<>();
                for (TrackYamlDTO.AssetYamlDTO asset : l.assets) {
                    if (asset.path == null || asset.path.isBlank()) continue;

                    Path src = repoRoot.resolve(asset.path).normalize();
                    if (!Files.exists(src)) {
                        LOG.warn("Asset not found: " + src + " for lab " + lab.name);
                        continue;
                    }

                    String destUrl = saveFileToStorage(src, trackStorage.resolve("lab-" + lab.id), src.getFileName().toString());
                    Asset a = new Asset();
                    a.path = destUrl;
                    a.type = asset.type != null ? asset.type : guessTypeFromName(src.getFileName().toString());
                    a.lab = lab;
                    a.persist();
                    savedAssets.add(a);
                    LOG.info("Saved asset: " + destUrl);
                }
                lab.assets = savedAssets;
            }

            // ---- NEW: exercises ----
            if (l.exercises != null && !l.exercises.isEmpty()) {
                List<Exercise> savedExercises = new ArrayList<>();
                for (TrackYamlDTO.ExerciseYamlDTO ex : l.exercises) {
                    Exercise exercise = new Exercise();
                    exercise.title = ex.title;
                    exercise.type = ex.type;
                    exercise.question = ex.question;

                    // convert options list to JSON string for storage (simple)
                    if (ex.options != null && !ex.options.isEmpty()) {
                        // naive JSON array serialization
                        StringBuilder sb = new StringBuilder();
                        sb.append("[");
                        for (int i = 0; i < ex.options.size(); i++) {
                            String opt = ex.options.get(i).replace("\"", "\\\"");
                            sb.append("\"").append(opt).append("\"");
                            if (i < ex.options.size() - 1) sb.append(",");
                        }
                        sb.append("]");
                        exercise.optionsJson = sb.toString();
                    } else {
                        exercise.optionsJson = null;
                    }

                    exercise.correctAnswer = ex.correctAnswer;
                    exercise.hint = ex.hint;
                    exercise.points = ex.points != null ? ex.points : 0;
                    exercise.lab = lab;

                    exercise.persist();
                    savedExercises.add(exercise);
                    //LOG.infof("Persisted exercise '%s' (id=%d) for lab '%s'", exercise.title, exercise.id, lab.name);
                }
                lab.exercises = savedExercises;
            }

            persistedLabs.add(lab);
        }

        // link labs to track and persist
        track.labs = persistedLabs;
        track.persist(); // update track with labs list
        //LOG.infof("Imported %d labs for track %s", persistedLabs.size(), track.name);
    }


    // -----------------------
    // Helper utilities
    // -----------------------

    private LearningTrack.Difficulty parseDifficulty(String diff) {
        if (diff == null) return LearningTrack.Difficulty.BEGINNER;
        try {
            return LearningTrack.Difficulty.valueOf(diff.trim().toUpperCase());
        } catch (Exception e) {
            LOG.warn("Unknown difficulty '" + diff + "', defaulting to BEGINNER");
            return LearningTrack.Difficulty.BEGINNER;
        }
    }

    private boolean looksLikePath(String s) {
        return s.contains("/") || s.endsWith(".md") || s.endsWith(".html") || s.contains(".");
    }

    private String guessTypeFromName(String fileName) {
        String lower = fileName.toLowerCase();
        if (lower.endsWith(".png") || lower.endsWith(".jpg") || lower.endsWith(".jpeg") || lower.endsWith(".svg"))
            return "image";
        if (lower.endsWith(".js") || lower.endsWith(".ts") || lower.endsWith(".py") || lower.endsWith(".java"))
            return "code";
        if (lower.endsWith(".pdf")) return "pdf";
        return "file";
    }

    /**
     * Save a file (src) into storage under destBase (directory) with filename.
     * Returns a relative URL/path string that can be stored in DB.
     */
    private String saveFileToStorage(Path src, Path destBase, String filename) throws IOException {
        Files.createDirectories(destBase);
        // keep filename unique by prefixing timestamp
        String safeName = System.currentTimeMillis() + "-" + sanitizeForPath(filename);
        Path dest = destBase.resolve(safeName);
        Files.copy(src, dest, StandardCopyOption.REPLACE_EXISTING);

        // Return a URL-like path. In production you would return an S3 URL or CDN path
        return dest.toAbsolutePath().toString();
    }

    private String sanitizeForPath(String s) {
        return s.replaceAll("[^a-zA-Z0-9\\-_\\.]", "-").toLowerCase();
    }
}
