package org.nextgen.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;

@Entity
public class BadgeCriteria extends BaseEntity {
    public enum CriteriaType {
        MILESTONE_COMPLETED,
        STAGE_COMPLETED,
        TOTAL_POINTS_REACHED,
        JOURNEY_COMPLETED
    }
    
    @ManyToOne
    public Badge badge;

    @Enumerated(EnumType.STRING)
    public CriteriaType type;

    public Long milestoneId;
    public Long stageId;
    public Integer pointsThreshold;
}
