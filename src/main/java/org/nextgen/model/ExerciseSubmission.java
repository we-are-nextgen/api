package org.nextgen.model;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.util.List;
import java.util.UUID;
import java.time.LocalDateTime;

@Entity
@Table(name = "exercise_submission")
public class ExerciseSubmission extends BaseEntity {

    @ManyToOne
    @JsonbTransient
    public ITProfessional user;

    @ManyToOne
    @JsonbTransient
    public Exercise exercise;

    @Column(name = "exercise_id", insertable=false, updatable=false)
    public UUID exerciseId;


    @Column(columnDefinition = "TEXT")
    public String answer;

    public boolean correct;

    public Integer earnedPoints;

    @Column (name = "submitted_at")
    public LocalDateTime submittedAt;

    public static ExerciseSubmission findByUserAndExercise(ITProfessional user, Exercise exercise) {
        return find("user = ?1 and exercise = ?2", user, exercise).firstResult();
    }

    public static List<ExerciseSubmission> findByUserAndLab(ITProfessional user, Lab lab) {
        return find("user = ?1 and exercise.lab = ?2", user, lab).list();
    }

}
