package org.nextgen.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
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

    /**
     * Calculates the total earned points across all exercises within a specific Learning Track for a given user.
     * * @param userId The ID of the ITProfessional user.
     * @param learningTrackId The ID of the LearningTrack to filter by.
     * @return The total sum of earned points as a Long.
     */
    public static Long findTotalEarnedPointsByTrack(Long userId, Long learningTrackId) {
        EntityManager em = LearningTrack.getEntityManager();
        
        // JPQL Query: SELECT the SUM of earnedPoints
        String jpql = "SELECT SUM(es.earnedPoints) " +
                    "FROM LearningTrack lt " +
                    "JOIN lt.labs l " + // 1. Traverse LearningTrack -> Lab (using the ManyToMany 'labs' collection)
                    "JOIN l.exercises e " + // 2. Traverse Lab -> Exercise (using the OneToMany 'exercises' collection)
                    "JOIN ExerciseSubmission es ON es.exercise = e " + // 3. Traverse Exercise -> ExerciseSubmission
                    "WHERE lt.id = :learningTrackId " + // Filter by the specific Learning Track ID
                    "AND es.user.id = :userId"; // Filter by the specific user ID

        // Note: The query returns an Object (which is a Long) or null if no submissions exist.
        Object result = em.createQuery(jpql)
                        .setParameter("userId", userId)
                        .setParameter("learningTrackId", learningTrackId)
                        .getSingleResult();

        // Handle null result (which means no submissions found for the user/track)
        if (result == null) {
            return 0L;
        }
        
        // Explicitly cast the result (which is guaranteed to be a Long by SUM)
        return (Long) result;
    }
}