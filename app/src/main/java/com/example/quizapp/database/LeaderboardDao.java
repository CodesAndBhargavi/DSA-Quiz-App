package com.example.quizapp.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.example.quizapp.models.LeaderboardEntry;
import java.util.List;

@Dao
public interface LeaderboardDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<LeaderboardEntry> entries);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(LeaderboardEntry entry);

    @Query("SELECT * FROM leaderboard ORDER BY points DESC")
    List<LeaderboardEntry> getAllLeaderboard();

    @Query("SELECT * FROM leaderboard WHERE isCurrentUser = 1 LIMIT 1")
    LeaderboardEntry getCurrentUser();

    @Update
    void update(LeaderboardEntry entry);

    @Query("DELETE FROM leaderboard")
    void clearLeaderboard();
}
