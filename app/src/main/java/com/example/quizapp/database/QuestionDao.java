package com.example.quizapp.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.example.quizapp.models.Question;
import java.util.List;

@Dao
public interface QuestionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Question> questions);

    @Query("DELETE FROM questions")
    void deleteAll();

    @Query("SELECT COUNT(*) FROM questions")
    int getCount();

    @Query("SELECT * FROM questions WHERE topic = :topic AND difficulty = :difficulty")
    List<Question> getQuestionsByTopicAndDifficulty(String topic, String difficulty);

    @Query("SELECT * FROM questions WHERE difficulty = :difficulty")
    List<Question> getQuestionsByDifficulty(String difficulty);

    @Query("SELECT * FROM questions WHERE topic = :topic")
    List<Question> getQuestionsByTopic(String topic);

    @Query("SELECT * FROM questions")
    List<Question> getAllQuestions();
}
