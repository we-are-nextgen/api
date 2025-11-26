package org.nextgen.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "exercise_submission")
public class ExerciseSubmission extends BaseEntity {

    @ManyToOne
    public ITProfessional user;

    @ManyToOne
    public Exercise exercise;

    @Column(columnDefinition = "TEXT")
    public String submittedAnswer;

    public boolean correct;

    public Integer earnedPoints;

    public LocalDateTime submittedAt;
}
