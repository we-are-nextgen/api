package org.nextgen.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.util.List;

/**
 * Enable UserProfile JPA entity defined as a Panache Entity.
 */
@Entity 
public class ITProfessional extends BaseEntity {
    public String userId;
    @ManyToOne
    public Domain domain;
    @ManyToOne
    public Stage stage;
    @ManyToOne
    public Profile profile;
    
    @ManyToMany
    @JoinTable(
        name = "user_profiles", // name of the join table
        joinColumns = @JoinColumn(name = "user_id"), // foreign key to this entity
        inverseJoinColumns = @JoinColumn(name = "profile_id") // foreign key to the other entity
    )
    public List <Profile> selectedProfiles;
    

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    public List <UserBadge> badges;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    public List<UserProgress> userProgress;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    public List<UserTrack> userTracks;
    
    /* ------ Model Helper Methods ---------- */

    /**
     * get ITProfessional id by email
     * @param email
     * @return
     */
    public static Long getUserIdByEmail(String email) {
        ITProfessional user = ITProfessional.find("userId", email).firstResult();
        if (user != null) {
            return user.id;
        }
        return null;
    } 

    /**
     * get ITProfessional by email
     * @param email
     * @return
     */
    public static ITProfessional getUserByEmail(String email) {
        return ITProfessional.find("userId", email).firstResult();
    }  


    /**
     * get number of ITProfessionals in certain stage
     * @param stageId
     * @return
     */
    public static long countByStage(Long stageId) {
        return ITProfessional.count("stage.id", stageId);
    }
        
}
