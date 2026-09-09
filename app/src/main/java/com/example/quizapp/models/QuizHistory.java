package com.example.quizapp.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.io.Serializable;

@Entity(tableName = "quiz_history")
public class QuizHistory implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String topic;
    public String difficulty;
    public int score;
    public int totalQuestions;
    public int accuracyPercent;
    public int timeTakenSeconds;
    public int starsEarned;
    public long timestamp;

    public QuizHistory() {}

    public QuizHistory(String topic, String difficulty, int score, int totalQuestions,
                       int accuracyPercent, int timeTakenSeconds, int starsEarned, long timestamp) {
        this.topic = topic;
        this.difficulty = difficulty;
        this.score = score;
        this.totalQuestions = totalQuestions;
        this.accuracyPercent = accuracyPercent;
        this.timeTakenSeconds = timeTakenSeconds;
        this.starsEarned = starsEarned;
        this.timestamp = timestamp;
    }
}
