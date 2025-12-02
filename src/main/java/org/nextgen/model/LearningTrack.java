package org.nextgen.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.Column;
import java.util.List;



/**
 * Represents a piece of lab material, linked to multiple IT Profiles.
 */
@Entity
@Table (name = "Learning_track" )
public class LearningTrack extends BaseEntity {
    
    // Enum for difficulty levels
    public enum Difficulty {
        BEGINNER, INTERMEDIATE, ADVANCED
    }
    
    @Column(nullable = false)
    public String name;

    @Column(nullable = true)
    public String uuid;
    
    @Column(columnDefinition = "TEXT")
    public String description;
    
    
    @Column(name = "difficulty_level")
    public Difficulty difficultyLevel;
    
    
    @Column(name = "estimated_time_min")
    public Integer estimatedTimeMin;

    @Column(name = "reward_points")
    public Integer rewardPoints;

    public String icon;

    @JsonbTransient
    @ManyToOne
    public Domain domain;


    // Git Repo
    public String repoUrl;
    public String repoBranch;
    public String repoPath;

    
    // Many-to-Many relationship with Profile. 
    // Profile is defined as the owning side of this relationship.
    @ManyToMany
    @JoinTable(
        name = "learning_track_profiles", // The junction table name
        joinColumns = @JoinColumn(name = "learning_track_id"), // Foreign key column to this entity (Lab)
        inverseJoinColumns = @JoinColumn(name = "profile_id") // Foreign key column to the other entity (Profile)
    )
    public List<Profile> profiles;


    // Many-to-Many relationship with Profile. 
    // Lab is defined as the owning side of this relationship.
    @ManyToMany 
    @JoinTable(
        name = "learning_track_labs", // The junction table name
        joinColumns = @JoinColumn(name = "learning_track_id"), // Foreign key column to this entity (LearningTrack)
        inverseJoinColumns = @JoinColumn(name = "lab_id") // Foreign key column to the other entity (Lab)
    )
    @OrderBy("sequence ASC") 
    public List<Lab> labs;

    // Helper method to find labs by difficulty
    public static List<LearningTrack> findByDifficulty(Difficulty difficulty) {
        return list("difficultyLevel", difficulty);
    }

    public static List<LearningTrack> findByDomainName(String domainName) {
        return list("domain.name", domainName);
    }

    public static List<LearningTrack> findByDomainId(Long domainId) {
        return list("domain.id", domainId);
    }

    public static List<LearningTrack> findByDomainPaginated(Long domainId, int page, int pageSize) {
    return find("domain.id", domainId)
            .page(page, pageSize)
            .list();
}
}