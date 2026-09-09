package com.example.quizapp.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.quizapp.models.QuizHistory;
import java.util.List;

@Dao
public interface HistoryDao {
    @Insert
    void insert(QuizHistory history);

    @Query("SELECT * FROM quiz_history ORDER BY timestamp DESC")
    List<QuizHistory> getAllHistory();

    @Query("SELECT * FROM quiz_history ORDER BY timestamp DESC LIMIT :limit")
    List<QuizHistory> getRecentHistory(int limit);

    @Query("SELECT COUNT(*) FROM quiz_history")
    int getTotalQuizzes();

    @Query("SELECT COALESCE(SUM(starsEarned), 0) FROM quiz_history")
    int getTotalStars();

    @Query("SELECT COALESCE(AVG(accuracyPercent), 0) FROM quiz_history")
    int getAverageAccuracy();

    @Query("SELECT COUNT(*) FROM quiz_history WHERE topic = :topic")
    int getQuizzesByTopic(String topic);

    @Query("SELECT COALESCE(AVG(accuracyPercent), 0) FROM quiz_history WHERE topic = :topic")
    int getAverageAccuracyByTopic(String topic);

    @Query("DELETE FROM quiz_history")
    void clearHistory();
}
