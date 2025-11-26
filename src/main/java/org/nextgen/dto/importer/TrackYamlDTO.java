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

    // -----------------------
    // LAB DTO
    // -----------------------
    public static class LabYamlDTO {
        public String name;
        public String UUID;
        public String description;
        public String icon;
        public String difficultyLevel;
        public Integer estimatedTimeMin;
        public boolean hasBonusTasks;
        public Integer sequence;

        public String contentMarkdown;
        public String contentHtml;

        public List<AssetYamlDTO> assets;

        // 🔥 NEW: Exercises inside Lab
        public List<ExerciseYamlDTO> exercises;
    }

    // -----------------------
    // EXERCISE DTO
    // -----------------------
    public static class ExerciseYamlDTO {
        public String title;
        public String type;           // MCQ, TEXT, CODE, TRUE_FALSE, MATCHING
        public String question;
        public List<String> options;  // For MCQ
        public String correctAnswer;
        public String hint;
        public Integer points;
    }

    // -----------------------
    // ASSET DTO
    // -----------------------
    public static class AssetYamlDTO {
        public String path;
        public String type;
    }
}
