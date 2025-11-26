package org.nextgen.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_lab_progress")
public class UserLabProgress extends BaseEntity {

    @ManyToOne
    @JsonbTransient
    public ITProfessional user;

    @ManyToOne
    @JsonbTransient
    public LearningTrack track;

    @ManyToOne
    @JsonbTransient
    public Lab lab;

    @Column(name = "lab_id", insertable=false, updatable=false)
    public Long labId;

    public Boolean completed;

    @Column(name = "start_date")
    public LocalDateTime startdate;

    @Column(name = "completed_at")
    public LocalDateTime completedAt;

    // Helper: Find all labs completed by user in a track
    public static List<UserLabProgress> completedForTrack(Long userId, Long trackId) {
        return find("user.id = ?1 AND lab.id IN (SELECT l.id FROM LearningTrack t JOIN t.labs l WHERE t.id=?2) AND completed = true",
                userId, trackId).list();
    }
    
}
