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
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.jgit.api.Git;
import org.jboss.logging.Logger;
import org.nextgen.dto.importer.BootcampYamlDTO;
import org.nextgen.dto.importer.BootcampYamlDTO.LayerDTO;
import org.nextgen.dto.importer.BootcampYamlDTO.ModuleDTO;
import org.nextgen.dto.importer.ImportParamDTO;
import org.nextgen.model.Bootcamp;
import org.nextgen.model.Component;
import org.nextgen.model.Layer;
import org.nextgen.model.Module;

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

        // 3. Find BOOTCAMP_YAML
        File bootcampYamlFile = new File(pathInsideRepo, BOOTCAMP_YAML);
        if (!bootcampYamlFile.exists()) {
            throw new RuntimeException(BOOTCAMP_YAML + "not found at: " + bootcampYamlFile.getPath());
        }

        // 4. Parse YAML
        Yaml yaml = new Yaml();
        BootcampYamlDTO yamlDTO = yaml.loadAs(Files.newInputStream(bootcampYamlFile.toPath()), BootcampYamlDTO.class);

        // 5. Validate YAML
        validateYaml(yamlDTO);

        // 6. Store Bootcamp in DB
        Bootcamp bootcamp = saveBootcamp(yamlDTO, dto);
        
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

    private Bootcamp saveBootcamp(BootcampYamlDTO bootcampDTO, ImportParamDTO dto) {
        Bootcamp bootcamp = new Bootcamp();
        bootcamp.name=bootcampDTO.name;
        bootcamp.description=bootcampDTO.description;
        bootcamp.badgeRuleValue = bootcampDTO.badgeRuleValue;
        bootcamp.version=bootcampDTO.version;
        bootcamp.durationWeeks=bootcampDTO.durationWeeks;
        bootcamp.audience = bootcampDTO.audience;
        bootcamp.outcomes = bootcampDTO.outcomes;
        bootcamp.expectedStartDate = LocalDate.parse(bootcampDTO.expectedStartDate);
        bootcamp.persist();
        for(LayerDTO layerDTO : bootcampDTO.layers){
            Layer layer = new Layer();
            layer.bootcamp = bootcamp;
            layer.name = layerDTO.name;
            layer.type = layerDTO.type;
            layer.persist();
            for(ModuleDTO moduleDTO : layerDTO.modules){
                Module module = new Module();
                module.layer = layer;
                module.name = moduleDTO.name;
                module.type = moduleDTO.type;
                module.objectives = moduleDTO.objectives;
                module.linkedLabs = moduleDTO.linkedLabs;
                if(moduleDTO.components!=null) {
                    List<Component> list = moduleDTO.components.entrySet().stream()
                        .map(e -> {
                            Component c = new Component();
                            c.type= e.getKey();
                            c.reference = e.getValue();
                            c.module = module;
                            return c;
                        })
                        .collect(Collectors.toList());
                    module.components = list;
                }
                module.persist();
            }
        }
        return bootcamp;
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
