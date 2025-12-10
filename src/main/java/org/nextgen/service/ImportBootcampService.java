package org.nextgen.service;

import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.eclipse.jgit.api.Git;
import org.jboss.logging.Logger;
import org.nextgen.dto.importer.BootcampYamlDTO;
import org.nextgen.dto.importer.ImportParamDTO;
import org.nextgen.model.Bootcamp;

@ApplicationScoped
public class ImportBootcampService {
    private static final String BOOTCAMP_YAML = "bootcamp.yaml";
    // private static final Logger LOG = Logger.getLogger(ImportBootcampService.class);

    // Base storage directory for imported assets (can be configured)
    // Example: set to /var/lib/nextgen/assets in prod via config
    //private final Path assetBaseDir = Paths.get(System.getProperty("nextgen.assets.path", "/tmp/nextgen-assets"));


     @Transactional
    public Bootcamp importTrack(ImportParamDTO dto) throws Exception {
        // 1. Clone repository
        Path cloneDir = Files.createTempDirectory("bootcamp-import-");
        Git.cloneRepository()
            .setURI(dto.gitRepoUrl)
            .setDirectory(cloneDir.toFile())
            .setBranch(dto.gitBranch)
            .call();

        // 2. Resolve gitPath inside repo
        File pathInsideRepo = dto.gitPath == null || dto.gitPath.isBlank() ?
                cloneDir.toFile() :
                cloneDir.resolve(dto.gitPath).toFile();

        // 3. Find track.yaml
        File bootcampYamlFile = new File(pathInsideRepo, BOOTCAMP_YAML);
        if (!bootcampYamlFile.exists()) {
            throw new RuntimeException(BOOTCAMP_YAML + "not found at: " + bootcampYamlFile.getPath());
        }

        // 4. Parse YAML
        Yaml yaml = new Yaml();
        BootcampYamlDTO yamlDTO = yaml.loadAs(Files.newInputStream(bootcampYamlFile.toPath()), BootcampYamlDTO.class);

        // 5. Validate YAML
        validateYaml(yamlDTO);

        // 6. Store LearningTrack in DB
        Bootcamp bootcamp = saveBootcamp(yamlDTO, dto);

        // 7. Process labs + assets + exercises
        // Path labsPath = cloneDir.resolve(dto.gitPath == null ? "" : dto.gitPath);
        // System.out.println("Repo URL: ======== " + dto.gitRepoUrl); // --- IGNORE ---
        // saveLabsFromRepo(yamlDTO, labsPath, track, dto);

        return bootcamp;
    }

    private void validateYaml(BootcampYamlDTO dto) {
        if (dto.name == null || dto.name.isBlank()) {
            throw new RuntimeException("Track name is required in track.yaml");
        }
        //if (dto.layers == null || dto.layers.isEmpty()) {
        //    throw new RuntimeException("Track must contain at least one lab");
        //}
    }

    private Bootcamp saveBootcamp(BootcampYamlDTO yamlDTO, ImportParamDTO dto) {
        return null;
    }

    public BootcampYamlDTO loadBootcampFromYaml(String yamlPath) {
        LoaderOptions loaderOptions = new LoaderOptions();
        loaderOptions.setAllowDuplicateKeys(true);   // optional, recommended

        Yaml yaml = new Yaml(new Constructor(BootcampYamlDTO.class, loaderOptions));

        InputStream input = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream(yamlPath);

        if (input == null) {
            throw new RuntimeException("YAML file not found: " + yamlPath);
        }

        return yaml.load(input);
    }
}
