package org.nextgen.model;

import java.time.LocalDateTime;
import java.util.UUID;

import org.nextgen.model.Observers.BadgeRewardObserver;
import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * 
 */
@Entity
@Table(name = "user_tracks")
@EntityListeners(BadgeRewardObserver.class)
public class UserTrack extends BaseProgress {
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonbTransient
    public ITProfessional user;

    @ManyToOne
    @JoinColumn(name = "learning_track_id", insertable = false, updatable = false)
    @JsonbTransient
    public LearningTrack track;

    @Column(name = "learning_track_id")
    public UUID learningTrackId;

    public UUID getLearningTrackId() {
        return learningTrackId;
    }

    public void setLearningTrackId(UUID learningTrackId) {
        this.learningTrackId = learningTrackId;
    }

    // Extra column!
    public Boolean enrolled;

    public Boolean completed;

    @Column(name = "enrolled_at")
    public LocalDateTime enrolledAt;

    @Column(name = "finished_at")
    public LocalDateTime finishedAt;

    
    



}
