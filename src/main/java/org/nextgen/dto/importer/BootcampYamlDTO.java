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
    public List<LayerDTO> layers;
    public String badgeRuleValue;
    public String difficultyLevel;
    public Integer rewardPoints;
    public String expectedStartDate;
    public Integer capacity;



    public static class LayerDTO {
        public String name;
        public String type;
        public List<ModuleDTO> modules;
    }

    public static class ModuleDTO {
        public String id;
        public String name;
        public String type;          // live, quiz, lab, etc.
        public String description;   // practice modules
        public String scenario;      // practice modules

        public List<String> objectives;
        public List<String> resources;
        public List<String> linkedLabs;

        public List<String> deliverables;   // capstone X1
        public List<String> tasks;          // capstone X2
        public String format;               // capstone X3
        public String badge;                // capstone X4

        public Map<String, String> components; // primer/demo/lab/quiz/discussion
    }


    
}
