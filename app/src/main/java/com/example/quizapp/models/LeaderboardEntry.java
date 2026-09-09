package com.example.quizapp.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.io.Serializable;

@Entity(tableName = "leaderboard")
public class LeaderboardEntry implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String userName;
    public int points;
    public String avatarEmoji;
    public int rank;
    public boolean isCurrentUser;

    public LeaderboardEntry() {}

    public LeaderboardEntry(String userName, int points, String avatarEmoji, int rank, boolean isCurrentUser) {
        this.userName = userName;
        this.points = points;
        this.avatarEmoji = avatarEmoji;
        this.rank = rank;
        this.isCurrentUser = isCurrentUser;
    }
}
