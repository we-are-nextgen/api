package org.nextgen.dto.importer;

import java.util.List;
import java.util.Map;

public class BootcampYamlDTO {
    
    public String name;
    public String version;
    public String description;
    public Integer durationWeeks;
    public List<String> audience;
    public List<String> outcomes;
    public LayersDTO layers;

    public static class ModuleDTO {
        public String id;
        public String name;
        public String type;           // optional for certain modules
        public String description;    // optional
        public List<String> objectives;
        public List<String> linkedLabs;
        public Map<String, String> components; // primer, demo, lab, quiz, discussion
        public String scenario;        // challenge modules
        public List<String> tasks;     // capstone tasks
        public Object deliverables;    // capstone design deliverables
        public Object format;          // showcase
        public Object badge_id;        // badge
    }


    public static class LayersDTO {
        public List<ModuleDTO> orientation;
        public List<ModuleDTO> core_modules;
        public List<ModuleDTO> practice_modules;
        public List<ModuleDTO> capstone;
    }
}
