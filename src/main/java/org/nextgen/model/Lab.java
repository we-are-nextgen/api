package org.nextgen.model;

import org.nextgen.model.LearningTrack.Difficulty;

import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;

@Entity
public class Lab extends BaseEntity{
    
    @Column(nullable = false)
    public String name;

    @Column(nullable = true)
    public String uuid;

    public String description;
    
    public String icon;

    @Column(name = "difficulty_level")
    public Difficulty difficultyLevel;
    
    @Column(name = "content_markdown")
    public String contentMarkdown;

    @Column(name = "content_html")
    public String contentHtml;

    @Column(name = "content_html_url")
    public String contentHtmlUrl;

    @Column(name = "content_markdown_url")
    public String contentMarkdownUrl;
    

    @Column(length = 2048)
    public String url;

     @Column(length = 2048)
    public String youtube;
    
    @Column(name = "estimated_time_min")
    public Integer estimatedTimeMin;

    @Column(name = "has_bonus_tasks")
    public Boolean hasBonusTasks;

    @OneToMany(mappedBy = "lab", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    public List<Asset> assets;

}
