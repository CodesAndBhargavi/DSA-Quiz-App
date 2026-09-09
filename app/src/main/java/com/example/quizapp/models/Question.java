package com.example.quizapp.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.io.Serializable;

@Entity(tableName = "questions")
public class Question implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id;
    
    public String topic;        // "Arrays", "LinkedList", "StacksQueues", "Trees", "Graphs", "Sorting", "Searching", "DP", "TimeComplexity"
    public String difficulty;   // "Basic", "Medium", "Hard"
    public String questionText;
    public String optionA;
    public String optionB;
    public String optionC;
    public String optionD;
    public String correctOption; // "A", "B", "C", "D"
    public String explanation;

    public Question() {}

    public Question(String topic, String difficulty, String questionText,
                    String optionA, String optionB, String optionC, String optionD,
                    String correctOption, String explanation) {
        this.topic = topic;
        this.difficulty = difficulty;
        this.questionText = questionText;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.correctOption = correctOption;
        this.explanation = explanation;
    }
}
