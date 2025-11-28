package org.nextgen.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.nextgen.dto.*;
import org.nextgen.model.Exercise;
import org.nextgen.model.ExerciseSubmission;
import org.nextgen.model.ITProfessional;
import org.nextgen.model.Lab;

import java.time.LocalDateTime;
import java.util.*;

@ApplicationScoped
public class AutoGradingService {

    public Map<Object, Object> grade(LabSubmissionDTO labSubmissionDTO) {
        // TODO: limit number of attempts per user per exercise [3 attempts]

        // expected output format:
        // {"earned":8,"details":{"1":5,"2":3,"3":0,"4":0},"total":20}
        
        int earned = 0;
        Lab lab = Lab.findById(labSubmissionDTO.labId);
        int total = lab.exercises.stream().mapToInt(e -> e.points).sum();

        ITProfessional user = ITProfessional.getUserByEmail(labSubmissionDTO.userId);
        List<ExerciseSubmissionDTO> submissions = labSubmissionDTO.submissions;

        Map<Long, Integer> results = new HashMap<>();

        for (Exercise ex : lab.exercises) {
            ExerciseSubmissionDTO sub = submissions.stream()
                    .filter(s -> s.exerciseId.equals(ex.id))
                    .findFirst()
                    .orElse(null);

            if (sub == null) {
                results.put(ex.id, 0);
                continue;
            }
            
            int score = switch (ex.type.toUpperCase()) {
                case "MCQ" -> gradeMCQ(ex, sub.answer);
                case "TRUE_FALSE" -> gradeTrueFalse(ex, sub.answer);
                case "TEXT" -> gradeText(ex, sub.answer);
                case "CODE" -> gradeCode(ex, sub.answer);
                default -> 0;
            };
            earned += score;
            // Store the score for this exercise
            
            ExerciseSubmission submission = ExerciseSubmission.findByUserAndExercise(user, ex);

            if (submission != null) {
                submission.earnedPoints = score;
                submission.answer = sub.answer;
                submission.submittedAt = LocalDateTime.now();
                submission.correct = score == ex.points;
                submission.persist();
            } else {
                submission = new ExerciseSubmission();
                submission.exercise = ex;
                submission.earnedPoints  = score;
                submission.answer = sub.answer;
                submission.submittedAt = LocalDateTime.now();
                submission.correct = score == ex.points;
                submission.user = user;
                submission.persist();
            };
            // Here you would typically save submissionRecord to the database   
            results.put(ex.id, score);
        }
        return 
            Map.of(
                "earned", earned,
                "total", total,
                "details", results
            );

        //return results;
    }

    private int gradeMCQ(Exercise ex, String userAnswer) {
        return ex.correctAnswer.trim().equalsIgnoreCase(userAnswer.trim())
                ? ex.points
                : 0;
    }

    private int gradeTrueFalse(Exercise ex, String userAnswer) {
        return ex.correctAnswer.equalsIgnoreCase(userAnswer)
                ? ex.points
                : 0;
    }

    private int gradeText(Exercise ex, String userAnswer) {
        // Simple keyword-based evaluation
        return userAnswer.trim().length() > 10
                ? ex.points
                : 0;
    }

    private int gradeCode(Exercise ex, String userAnswer) {
        // Normalized comparison
        String expected = ex.correctAnswer.replaceAll("\\s+", "");
        String actual = userAnswer.replaceAll("\\s+", "");

        return expected.equals(actual) ? ex.points : 0;
    }
}
