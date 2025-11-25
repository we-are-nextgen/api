package org.nextgen.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

@Entity
public class Exercise extends BaseEntity {

    @ManyToOne
    public Lab lab;

    public String title;

    @Column(columnDefinition = "TEXT")
    public String question;

    // MCQ, CODE, TEXT, TRUE_FALSE, MATCHING, etc.
    public String type;

    // For MCQ or predefined answers
    @Column(columnDefinition = "TEXT")
    public String optionsJson;

    // Author-defined correct answer
    @Column(columnDefinition = "TEXT")
    public String correctAnswer;

    // Optional hints
    @Column(columnDefinition = "TEXT")
    public String hint;

    // How much this exercise is worth
    public Integer points;
}
