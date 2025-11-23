package org.nextgen.dto;

public class ImportLabDTO {

    private String name;
    private String description;
    private String icon;
    private String difficultyLevel; // BEGINNER, INTERMEDIATE, ADVANCED
    private String contentMarkdown;
    private String contentHtml;
    private String url;
    private String youtube;
    private Integer estimatedTimeMin;
    private boolean hasBonusTasks;

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getIcon() { return icon; }
    public void setIcon(String icon) { this.icon = icon; }
    public String getDifficultyLevel() { return difficultyLevel; }
    public void setDifficultyLevel(String difficultyLevel) { this.difficultyLevel = difficultyLevel; }
    public String getContentMarkdown() { return contentMarkdown; }
    public void setContentMarkdown(String contentMarkdown) { this.contentMarkdown = contentMarkdown; }
    public String getContentHtml() { return contentHtml; }
    public void setContentHtml(String contentHtml) { this.contentHtml = contentHtml; }
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
    public String getYoutube() { return youtube; }
    public void setYoutube(String youtube) { this.youtube = youtube; }
    public Integer getEstimatedTimeMin() { return estimatedTimeMin; }
    public void setEstimatedTimeMin(Integer estimatedTimeMin) { this.estimatedTimeMin = estimatedTimeMin; }
    public boolean isHasBonusTasks() { return hasBonusTasks; }
    public void setHasBonusTasks(boolean hasBonusTasks) { this.hasBonusTasks = hasBonusTasks; }
}
