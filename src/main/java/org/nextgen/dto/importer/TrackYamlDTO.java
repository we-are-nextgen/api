package org.nextgen.dto.importer;

import java.util.List;

public class TrackYamlDTO {
    public String name;
    public String UUID;
    public String description;
    public String icon;
    public String difficultyLevel;
    public Integer estimatedTimeMin;
    public Integer rewardPoints;
    public String domainName;
    public String community;

    public List<LabYamlDTO> labs;

    public static class LabYamlDTO {
        public String name;
        public String UUID;
        public String description;
        public String icon;
        public String difficultyLevel;
        public Integer estimatedTimeMin;
        public boolean hasBonusTasks;

        public String contentMarkdown;
        public String contentHtml;

        public List<AssetYamlDTO> assets;
    }

    public static class AssetYamlDTO {
        public String path;
        public String type;
    }
}
