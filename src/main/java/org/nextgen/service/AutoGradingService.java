package org.nextgen.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.nextgen.dto.*;
import org.nextgen.model.Exercise;
import org.nextgen.model.Lab;

import java.util.*;

@ApplicationScoped
public class AutoGradingService {

    public Map<Long, Integer> grade(Lab lab, List<ExerciseSubmissionDTO> submissions) {

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

            results.put(ex.id, score);
        }

        return results;
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
