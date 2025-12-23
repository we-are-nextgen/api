package org.nextgen.model;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import org.nextgen.model.LearningTrack.Difficulty;


@Entity
public class Lab extends BaseRewardable {
    
    @Column(nullable = false)
    public String name;

    @Column(nullable = true)
    public String uuid;

    public String language;

    public String description;
    
    public String icon;

    public Integer sequence;

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

    @OneToMany(mappedBy = "lab", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    public List<Exercise> exercises;

    /* -- Model Helper Methods -- */

    /**
     * Executes a query to get a list of Labs and the total earned points 
     * for those Labs, filtered by a specific ITProfessional user.
     * * @param userId The ID of the ITProfessional user.
     * @return List<Object[]> where each element is [Lab, Long totalEarnedPoints].
     */
    public static List<Object[]> findLabsWithTotalEarnedPoints(UUID userId) {
        EntityManager em = Lab.getEntityManager();
        
        // The query groups results by Lab, summing points from submissions 
        // filtered by the user.
        String jpql = "SELECT l, SUM(es.earnedPoints) " +
                    "FROM Lab l " +
                    "JOIN l.exercises e " + // Traverse Lab -> Exercise
                    "JOIN ExerciseSubmission es ON es.exercise = e " + // Traverse Exercise -> Submission
                    "WHERE es.user.id = :userId " + // Filter by the specific user ID
                    "GROUP BY l.id, l.name, l.description, l.uuid, l.language, l.icon, l.sequence, " +
                    "l.difficultyLevel, l.contentMarkdown, l.contentHtml, l.contentHtmlUrl, " +
                    "l.contentMarkdownUrl, l.url, l.youtube, l.estimatedTimeMin, l.hasBonusTasks " +
                    "ORDER BY l.sequence ASC";

        // Note: The GROUP BY clause must include all non-aggregated columns 
        // (i.e., all columns selected from the Lab entity, but using the Lab object 'l' 
        // is often simpler, though less performant than selecting only ID and Name).
        
        return em.createQuery(jpql, Object[].class)
                .setParameter("userId", userId)
                .getResultList();
    }

    /**
     * Executes a query to get Labs and the total earned points for those Labs, 
     * filtered by a specific ITProfessional user AND a specific LearningTrack.
     * * @param userId The ID of the ITProfessional user.
     * @param learningTrackId The ID of the LearningTrack to filter by.
     * @return List<Object[]> where each element is [Lab, Long totalEarnedPoints].
     */
    public static List<Object[]> findLabsWithTotalEarnedPointsByTrack(UUID userId, UUID learningTrackId) {
        EntityManager em = Lab.getEntityManager();
        
        // The query groups results by Lab, summing points from submissions 
        // filtered by user and constrained by the LearningTrack.
        String jpql = "SELECT l, SUM(es.earnedPoints) " +
                    "FROM LearningTrack lt " +
                    "JOIN lt.labs l " + // 1. Traverse LearningTrack -> Lab
                    "JOIN l.exercises e " + // 2. Traverse Lab -> Exercise
                    "JOIN ExerciseSubmission es ON es.exercise = e " + // 3. Traverse Exercise -> Submission
                    "WHERE lt.id = :learningTrackId " + // Filter by the Learning Track ID
                    "AND es.user.id = :userId " + // Filter by the specific user ID
                    "GROUP BY l.id, l.name, l.description, l.uuid, l.language, l.icon, l.sequence, " +
                    "l.difficultyLevel, l.contentMarkdown, l.contentHtml, l.contentHtmlUrl, " +
                    "l.contentMarkdownUrl, l.url, l.youtube, l.estimatedTimeMin, l.hasBonusTasks " +
                    "ORDER BY l.sequence ASC";
        
        // Note: We use the Entity Manager because this is a complex JOIN/GROUP BY query.
        return em.createQuery(jpql, Object[].class)
                .setParameter("userId", userId)
                .setParameter("learningTrackId", learningTrackId)
                .getResultList();
    }
}
