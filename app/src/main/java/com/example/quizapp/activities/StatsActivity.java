package com.example.quizapp.activities;

import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.quizapp.R;
import com.example.quizapp.database.AppDatabase;
import com.example.quizapp.utils.PreferenceManager;

public class StatsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        FrameLayout btnBackStats = findViewById(R.id.btnBackStats);
        TextView tvStatsAccuracy = findViewById(R.id.tvStatsAccuracy);
        TextView tvStatsPoints = findViewById(R.id.tvStatsPoints);
        TextView tvStatsSessions = findViewById(R.id.tvStatsSessions);
        TextView tvStatsStreak = findViewById(R.id.tvStatsStreak);

        AppDatabase db = AppDatabase.getInstance(this);
        PreferenceManager pref = new PreferenceManager(this);

        int accuracy = db.historyDao().getAverageAccuracy();
        int totalPoints = pref.getTotalPoints();
        int sessions = db.historyDao().getTotalQuizzes();
        int streak = pref.getCurrentStreak();

        tvStatsAccuracy.setText(accuracy + "%");
        tvStatsPoints.setText(totalPoints + " pts");
        tvStatsSessions.setText(String.valueOf(sessions));
        tvStatsStreak.setText(streak + " Days");

        btnBackStats.setOnClickListener(v -> finish());
    }
}
