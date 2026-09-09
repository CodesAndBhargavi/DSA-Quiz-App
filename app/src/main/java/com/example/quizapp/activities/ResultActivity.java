package com.example.quizapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.quizapp.R;
import com.example.quizapp.models.QuizSessionItem;
import java.io.Serializable;
import java.util.List;

public class ResultActivity extends AppCompatActivity {

    private String topic, difficulty;
    private int score, totalQuestions, accuracy, timeSeconds, pointsEarned, maxStreak;
    private List<QuizSessionItem> reviewList;

    private TextView tvResultBadge, tvResultTitle, tvResultScore;
    private TextView tvResultPoints, tvResultAccuracy, tvResultTime, tvResultStreak;
    private TextView btnReviewAnswers, btnPlayAgain, btnLeaderboardResult, btnHomeResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        topic = getIntent().getStringExtra("TOPIC");
        difficulty = getIntent().getStringExtra("DIFFICULTY");
        score = getIntent().getIntExtra("SCORE", 0);
        totalQuestions = getIntent().getIntExtra("TOTAL_QUESTIONS", 5);
        accuracy = getIntent().getIntExtra("ACCURACY", 0);
        timeSeconds = getIntent().getIntExtra("TIME_SECONDS", 0);
        pointsEarned = getIntent().getIntExtra("POINTS_EARNED", 0);
        maxStreak = getIntent().getIntExtra("MAX_STREAK", 0);
        reviewList = (List<QuizSessionItem>) getIntent().getSerializableExtra("REVIEW_ITEMS");

        initViews();
        displayResults();
        setupListeners();
    }

    private void initViews() {
        tvResultBadge = findViewById(R.id.tvResultBadge);
        tvResultTitle = findViewById(R.id.tvResultTitle);
        tvResultScore = findViewById(R.id.tvResultScore);
        tvResultPoints = findViewById(R.id.tvResultPoints);
        tvResultAccuracy = findViewById(R.id.tvResultAccuracy);
        tvResultTime = findViewById(R.id.tvResultTime);
        tvResultStreak = findViewById(R.id.tvResultStreak);

        btnReviewAnswers = findViewById(R.id.btnReviewAnswers);
        btnPlayAgain = findViewById(R.id.btnPlayAgain);
        btnLeaderboardResult = findViewById(R.id.btnLeaderboardResult);
        btnHomeResult = findViewById(R.id.btnHomeResult);
    }

    private void displayResults() {
        tvResultScore.setText("Score: " + score + " / " + totalQuestions + " (" + accuracy + "%)");
        tvResultPoints.setText("+" + pointsEarned + " pts");
        tvResultAccuracy.setText(accuracy + "%");
        tvResultTime.setText(timeSeconds + "s");
        tvResultStreak.setText(String.valueOf(maxStreak));

        if (score == totalQuestions) {
            tvResultBadge.setText("🏆");
            tvResultTitle.setText("Grandmaster Coder!");
        } else if (score >= totalQuestions / 2) {
            tvResultBadge.setText("🥈");
            tvResultTitle.setText("Great Performance!");
        } else {
            tvResultBadge.setText("📚");
            tvResultTitle.setText("Keep Practicing!");
        }
    }

    private void setupListeners() {
        // Review Answers Screen
        btnReviewAnswers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this, ReviewAnswersActivity.class);
                intent.putExtra("REVIEW_ITEMS", (Serializable) reviewList);
                startActivity(intent);
            }
        });

        // Play Again
        btnPlayAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this, TopicSelectionActivity.class);
                intent.putExtra("IS_PRACTICE_MODE", false);
                startActivity(intent);
                finish();
            }
        });

        // Leaderboard
        btnLeaderboardResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this, LeaderboardActivity.class);
                startActivity(intent);
            }
        });

        // Home
        btnHomeResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
    }
}
