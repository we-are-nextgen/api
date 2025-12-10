package org.nextgen.service;

import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import jakarta.enterprise.context.ApplicationScoped;
import java.io.InputStream;

import org.nextgen.dto.importer.BootcampYamlDTO;

@ApplicationScoped
public class ImportBootcampService {

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
