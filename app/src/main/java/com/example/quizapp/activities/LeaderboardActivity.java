package com.example.quizapp.activities;

import android.os.Bundle;
import android.widget.FrameLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.quizapp.R;
import com.example.quizapp.adapters.LeaderboardAdapter;
import com.example.quizapp.database.AppDatabase;
import com.example.quizapp.models.LeaderboardEntry;
import com.example.quizapp.utils.PreferenceManager;
import java.util.List;

public class LeaderboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        FrameLayout btnBackLeaderboard = findViewById(R.id.btnBackLeaderboard);
        RecyclerView rvLeaderboard = findViewById(R.id.rvLeaderboard);

        AppDatabase db = AppDatabase.getInstance(this);
        PreferenceManager pref = new PreferenceManager(this);

        // Update user's points in the leaderboard
        int userPoints = pref.getTotalPoints();
        LeaderboardEntry currentUser = db.leaderboardDao().getCurrentUser();
        if (currentUser != null) {
            currentUser.points = userPoints;
            db.leaderboardDao().update(currentUser);
        }

        List<LeaderboardEntry> list = db.leaderboardDao().getAllLeaderboard();

        rvLeaderboard.setLayoutManager(new LinearLayoutManager(this));
        LeaderboardAdapter adapter = new LeaderboardAdapter(this, list);
        rvLeaderboard.setAdapter(adapter);

        btnBackLeaderboard.setOnClickListener(v -> finish());
    }
}
