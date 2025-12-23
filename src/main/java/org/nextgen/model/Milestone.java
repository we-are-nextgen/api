package org.nextgen.model;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.json.bind.annotation.JsonbTransient;


@Entity
public class Milestone extends BaseRewardable {
    public String name;
    public String description;
    public Integer sequence;
    
    @Column (name ="required_points")
    public Integer requiredPoints;

    // if the milestone can be skipped and procced to 
    // the next milestone without completing it
    public Boolean skippable;

    @ManyToMany
    @JoinTable(
        name = "milestone_activity", // name of the join table
        joinColumns = @JoinColumn(name = "milestone_id"), // foreign key to this entity
        inverseJoinColumns = @JoinColumn(name = "activity_id") // foreign key to the other entity
    )
    public List <Activity> activities;

    @ManyToOne
    @JsonbTransient
    @JoinColumn(name = "stage_id", insertable = false, updatable = false)
    public Stage stage;

    @Column(name = "stage_id")
    public UUID stageId;   // <-- reference only ID

    public UUID getStageId() {
        return stageId;
    }

    public void setStageId(UUID stageId) {
        this.stageId = stageId;
    }

    /* -- Model Helper Methods -- */
    public static Milestone findNextMilestone(Integer squence){
        String query = "sequence > ?1";
        return Milestone.find(query, squence)
                    .firstResult();
    }

    public static Milestone findNextLearningTrackMilestone(Integer squence){
        String query = "SELECT m FROM Milestone m " +
                   "JOIN m.activities a " +
                   "WHERE m.sequence > ?1 AND a.name = ?2";
        return Milestone.find(query, 
                    squence, Activity.LEARNING_TRACK_ACTIVITY_NAME)
                    .firstResult();
    }

}