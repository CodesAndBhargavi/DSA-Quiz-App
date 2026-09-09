package com.example.quizapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.quizapp.R;
import com.example.quizapp.database.AppDatabase;
import com.example.quizapp.database.DataSeeder;
import com.example.quizapp.utils.DsaTips;
import com.example.quizapp.utils.PreferenceManager;
import java.util.Calendar;

public class HomeActivity extends AppCompatActivity {

    private TextView tvGreeting, tvTotalStars, tvAccuracy, tvSessions, tvTipContent;
    private LinearLayout cardStudy, cardPractice, cardQuiz, cardStats;
    private View btnSettings, btnLeaderboardTop;
    private PreferenceManager preferenceManager;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        preferenceManager = new PreferenceManager(this);
        db = AppDatabase.getInstance(this);
        DataSeeder.seedDatabaseIfEmpty(this);

        initViews();
        setupListeners();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateDashboardData();
    }

    private void initViews() {
        tvGreeting = findViewById(R.id.tvGreeting);
        tvTotalStars = findViewById(R.id.tvTotalStars);
        tvAccuracy = findViewById(R.id.tvAccuracy);
        tvSessions = findViewById(R.id.tvSessions);
        tvTipContent = findViewById(R.id.tvTipContent);

        cardStudy = findViewById(R.id.cardStudy);
        cardPractice = findViewById(R.id.cardPractice);
        cardQuiz = findViewById(R.id.cardQuiz);
        cardStats = findViewById(R.id.cardStats);

        btnSettings = findViewById(R.id.btnSettings);
        btnLeaderboardTop = findViewById(R.id.btnLeaderboardTop);
    }

    private void updateDashboardData() {
        // Dynamic greeting based on time of day
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);
        if (timeOfDay >= 0 && timeOfDay < 12) {
            tvGreeting.setText("Good Morning! ☀️");
        } else if (timeOfDay >= 12 && timeOfDay < 17) {
            tvGreeting.setText("Good Afternoon! 🌤️");
        } else {
            tvGreeting.setText("Good Evening! 🌙");
        }

        // Stats from Room DB & Prefs
        int totalStars = db.historyDao().getTotalStars();
        int accuracy = db.historyDao().getAverageAccuracy();
        int totalQuizzes = db.historyDao().getTotalQuizzes();

        tvTotalStars.setText(String.valueOf(totalStars));
        tvAccuracy.setText(accuracy + "%");
        tvSessions.setText(String.valueOf(totalQuizzes));

        // Tip of the Day
        tvTipContent.setText(DsaTips.getDailyTip(preferenceManager));
    }

    private void setupListeners() {
        // Study Mode
        cardStudy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, StudyActivity.class);
                startActivity(intent);
            }
        });

        // Practice Mode (untimed)
        cardPractice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, TopicSelectionActivity.class);
                intent.putExtra("IS_PRACTICE_MODE", true);
                startActivity(intent);
            }
        });

        // Timed Quiz Mode
        cardQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, TopicSelectionActivity.class);
                intent.putExtra("IS_PRACTICE_MODE", false);
                startActivity(intent);
            }
        });

        // Statistics
        cardStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, StatsActivity.class);
                startActivity(intent);
            }
        });

        // Leaderboard Top Icon
        btnLeaderboardTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, LeaderboardActivity.class);
                startActivity(intent);
            }
        });

        // Settings
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });
    }
}
