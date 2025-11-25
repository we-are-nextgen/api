package org.nextgen.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_lab_progress")
public class UserLabProgress extends BaseEntity {

    @ManyToOne
    public ITProfessional user;

    @ManyToOne
    public LearningTrack track;

    @ManyToOne
    public Lab lab;

    public boolean completed;

    public LocalDateTime startdate;
    public LocalDateTime closedate;
    
}
