package com.example.quizapp.models;

import java.io.Serializable;

public class QuizSessionItem implements Serializable {
    public Question question;
    public String selectedOption; // "A", "B", "C", "D" or null if skipped/timed out
    public boolean isCorrect;
    public int timeTakenSeconds;

    public QuizSessionItem(Question question, String selectedOption, boolean isCorrect, int timeTakenSeconds) {
        this.question = question;
        this.selectedOption = selectedOption;
        this.isCorrect = isCorrect;
        this.timeTakenSeconds = timeTakenSeconds;
    }
}
