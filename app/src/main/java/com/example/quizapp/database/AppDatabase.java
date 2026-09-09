package com.example.quizapp.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.example.quizapp.models.Question;
import com.example.quizapp.models.QuizHistory;
import com.example.quizapp.models.LeaderboardEntry;

@Database(entities = {Question.class, QuizHistory.class, LeaderboardEntry.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase INSTANCE;

    public abstract QuestionDao questionDao();
    public abstract HistoryDao historyDao();
    public abstract LeaderboardDao leaderboardDao();

    public static AppDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            "dsa_quiz_database"
                    ).allowMainThreadQueries().build();
                }
            }
        }
        return INSTANCE;
    }
}
