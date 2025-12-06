package org.nextgen.model;


import java.time.LocalDateTime;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.List;;


/**
 * Enable UserProgress JPA entity defined as a Panache Entity.
 * extends BaseProgress to inherit earnedPoints field
 */
@Entity
@Table (name = "user_progress" )
public class UserProgress extends BaseProgress  {

    public static String IN_PROGRESS = "In-Progress";
    public static String COMPLETED = "Completed";

    /**
     * Milestone implments IRewardable interface
     * with badgeRuleValue 
     */
    @ManyToOne    
    public Milestone milestone;

    public LocalDateTime startDate;
    public LocalDateTime closeDate;
    public String status; // e.g., "in-progress", "completed"
    
    @ManyToOne
    public Milestone nextMilestone; // next milestone to achieve
    @ManyToOne
    public UserProgress prevProgress; // previous progress record

    @ManyToOne
    @JsonbTransient
    public ITProfessional user;


    public static UserProgress findLastUserProgress(Long userId) {        
        String query = "user.id = ?1 order by id desc";
        return find(query,userId)
                .firstResult();
    }

    public static UserProgress getLastUserProgressByStatus(Long userId, String status) {        
        String query = "user.id = ?1 AND status = ?2 order by id desc";
        return find(query,userId, status)
                .firstResult();
    }


    /**
     * Finds UserProgress records associated with a specific user and a Milestone linked to a specific Activity name.
     * @param userId The ID of the ITProfessional user.
     * @param activityName The name of the Activity.
     * @return List of matching UserProgress records.
     */
    public static List<UserProgress> findByUserIdAndActivityName(Long userId, String activityName) {
        // JPQL Query explanation:
        // 1. SELECT up: Select the UserProgress entity.
        // 2. JOIN up.milestone m: Traverse from UserProgress to Milestone.
        // 3. JOIN m.activities a: Traverse from Milestone to the associated Activities.
        // 4. WHERE: Filter by the user's ID AND the Activity's name.
        
        String query = "SELECT up FROM UserProgress up " +
                    "JOIN up.milestone m " +
                    "JOIN m.activities a " +
                    "WHERE up.user.id = ?1 AND a.name = ?2";

        return find(query,userId, activityName)
                .list();
    }

     /**
     * Finds UserProgress records associated with a specific user and a Milestone linked to a specific Activity name.
     * @param userId The ID of the ITProfessional user.
     * @param activityName The name of the Activity.
     * @return List of matching UserProgress records.
     */
    public static UserProgress findLastByUserIdAndActivityName(Long userId, String activityName) {
        // JPQL Query explanation:
        // 1. SELECT up: Select the UserProgress entity.
        // 2. JOIN up.milestone m: Traverse from UserProgress to Milestone.
        // 3. JOIN m.activities a: Traverse from Milestone to the associated Activities.
        // 4. WHERE: Filter by the user's ID AND the Activity's name.
        
        String query = "SELECT up FROM UserProgress up " +
                   "JOIN up.milestone m " +
                   "JOIN m.activities a " +
                   "WHERE up.user.id = ?1 AND a.name = ?2 " +
                   "ORDER BY up.id DESC";

        return find(query,userId, activityName)
                .firstResult();
    }

    /**
     * Finds the count of UserProgress records associated with a specific user and a Milestone linked to a specific Activity name.
     * @param userId The ID of the ITProfessional user.
     * @param activityName The name of the Activity.
     * @return The total count of matching UserProgress records (as a Long).
     */
    public static Long countByUserIdAndActivityName(Long userId, String activityName) {
        // JPQL Query explanation:
        // 1. SELECT coun(up:) Select the count of UserProgress entity.
        // 2. JOIN up.milestone m: Traverse from UserProgress to Milestone.
        // 3. JOIN m.activities a: Traverse from Milestone to the associated Activities.
        // 4. WHERE: Filter by the user's ID AND the Activity's name.
        
        String query = "SELECT COUNT(up) FROM UserProgress up " +
                    "JOIN up.milestone m " +
                    "JOIN m.activities a " +
                    "WHERE up.user.id = ?1 AND a.name = ?2";
        
        Object result = find(query, userId, activityName).firstResult();

        if (result == null) return 0L;

        return ((Number) result).longValue();
    }
}
