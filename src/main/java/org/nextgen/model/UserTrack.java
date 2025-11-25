package org.nextgen.model;

import java.time.LocalDateTime;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_tracks")
public class UserTrack extends BaseEntity {
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonbTransient
    public ITProfessional user;

    @ManyToOne
    @JoinColumn(name = "learning_track_id", insertable = false, updatable = false)
    @JsonbTransient
    public LearningTrack track;

    @Column(name = "learning_track_id")
    public Long learningTrackId;

    public Long getLearningTrackId() {
        return learningTrackId;
    }

    public void setLearningTrackId(Long learningTrackId) {
        this.learningTrackId = learningTrackId;
    }

    // Extra column!
    public Boolean enrolled;

    @Column(name = "enrolled_at")
    public LocalDateTime enrolledAt;

    @Column(name = "finished_at")
    public LocalDateTime finishedAt;

}
