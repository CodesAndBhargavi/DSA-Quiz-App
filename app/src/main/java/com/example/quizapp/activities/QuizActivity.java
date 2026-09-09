package com.example.quizapp.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.example.quizapp.R;
import com.example.quizapp.database.AppDatabase;
import com.example.quizapp.models.Question;
import com.example.quizapp.models.QuizHistory;
import com.example.quizapp.models.QuizSessionItem;
import com.example.quizapp.utils.PreferenceManager;
import com.google.android.material.card.MaterialCardView;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class QuizActivity extends AppCompatActivity {

    private String topic = "All";
    private String difficulty = "Basic";
    private boolean isPracticeMode = false;

    private FrameLayout btnCloseQuiz;
    private ProgressBar quizProgressBar;
    private TextView tvQuizProgressText, tvQuizTimer, tvQuizTopic, tvQuizDifficulty, tvQuizStreak, tvQuizQuestionText;
    private LinearLayout layoutHeroQuestion;

    private LinearLayout optCard1, optCard2, optCard3, optCard4;
    private TextView optBadge1, optBadge2, optBadge3, optBadge4;
    private TextView optText1, optText2, optText3, optText4;
    private TextView optStatus1, optStatus2, optStatus3, optStatus4;

    private MaterialCardView cardQuizExplanation;
    private TextView tvQuizExpTitle, tvQuizExpText, btnQuizLifeline, btnQuizNext;

    private List<Question> questionList;
    private final List<QuizSessionItem> sessionReviewList = new ArrayList<>();
    private final Set<Integer> usedQuestionIds = new HashSet<>();

    private int currentIndex = 0;
    private int score = 0;
    private int streak = 0;
    private int maxStreak = 0;
    private int lifelinesRemaining = 1;
    private boolean answered = false;

    private CountDownTimer countDownTimer;
    private static final long QUESTION_TIMER_MILLIS = 30000;
    private long questionStartTime = 0;
    private long totalTimeSpentMillis = 0;

    private AppDatabase db;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        db = AppDatabase.getInstance(this);
        preferenceManager = new PreferenceManager(this);

        topic = getIntent().getStringExtra("TOPIC");
        if (topic == null) topic = "All";
        difficulty = getIntent().getStringExtra("DIFFICULTY");
        if (difficulty == null) difficulty = "Basic";
        isPracticeMode = getIntent().getBooleanExtra("IS_PRACTICE_MODE", false);

        initViews();
        loadSessionQuestions();
        startQuizSession();
    }

    private void initViews() {
        btnCloseQuiz = findViewById(R.id.btnCloseQuiz);
        quizProgressBar = findViewById(R.id.quizProgressBar);
        tvQuizProgressText = findViewById(R.id.tvQuizProgressText);
        tvQuizTimer = findViewById(R.id.tvQuizTimer);
        tvQuizTopic = findViewById(R.id.tvQuizTopic);
        tvQuizDifficulty = findViewById(R.id.tvQuizDifficulty);
        tvQuizStreak = findViewById(R.id.tvQuizStreak);
        tvQuizQuestionText = findViewById(R.id.tvQuizQuestionText);
        layoutHeroQuestion = findViewById(R.id.layoutHeroQuestion);

        optCard1 = findViewById(R.id.optCard1);
        optCard2 = findViewById(R.id.optCard2);
        optCard3 = findViewById(R.id.optCard3);
        optCard4 = findViewById(R.id.optCard4);

        optBadge1 = findViewById(R.id.optBadge1);
        optBadge2 = findViewById(R.id.optBadge2);
        optBadge3 = findViewById(R.id.optBadge3);
        optBadge4 = findViewById(R.id.optBadge4);

        optText1 = findViewById(R.id.optText1);
        optText2 = findViewById(R.id.optText2);
        optText3 = findViewById(R.id.optText3);
        optText4 = findViewById(R.id.optText4);

        optStatus1 = findViewById(R.id.optStatus1);
        optStatus2 = findViewById(R.id.optStatus2);
        optStatus3 = findViewById(R.id.optStatus3);
        optStatus4 = findViewById(R.id.optStatus4);

        cardQuizExplanation = findViewById(R.id.cardQuizExplanation);
        tvQuizExpTitle = findViewById(R.id.tvQuizExpTitle);
        tvQuizExpText = findViewById(R.id.tvQuizExpText);
        btnQuizLifeline = findViewById(R.id.btnQuizLifeline);
        btnQuizNext = findViewById(R.id.btnQuizNext);

        // Header info
        tvQuizTopic.setText("✦ " + topic);
        tvQuizDifficulty.setText(difficulty.toUpperCase());

        // Close button dialog
        btnCloseQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showExitConfirmation();
            }
        });

        // Option click listeners
        View.OnClickListener optionListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!answered) {
                    int selected = 0;
                    if (v.getId() == R.id.optCard2) selected = 1;
                    else if (v.getId() == R.id.optCard3) selected = 2;
                    else if (v.getId() == R.id.optCard4) selected = 3;

                    checkAnswer(selected);
                }
            }
        };

        optCard1.setOnClickListener(optionListener);
        optCard2.setOnClickListener(optionListener);
        optCard3.setOnClickListener(optionListener);
        optCard4.setOnClickListener(optionListener);

        // 50:50 Lifeline
        btnQuizLifeline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                use5050Lifeline();
            }
        });

        // Next Question
        btnQuizNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentIndex++;
                if (currentIndex < questionList.size()) {
                    displayQuestion();
                } else {
                    finishQuizSession();
                }
            }
        });
    }

    private void loadSessionQuestions() {
        List<Question> rawQuestions;
        if ("All".equalsIgnoreCase(topic)) {
            rawQuestions = db.questionDao().getQuestionsByDifficulty(difficulty);
        } else {
            rawQuestions = db.questionDao().getQuestionsByTopicAndDifficulty(topic, difficulty);
        }

        if (rawQuestions == null || rawQuestions.isEmpty()) {
            rawQuestions = db.questionDao().getAllQuestions();
        }

        // Shuffle questions to ensure variety
        Collections.shuffle(rawQuestions);

        // Filter no-repeat question IDs for this session
        questionList = new ArrayList<>();
        for (Question q : rawQuestions) {
            if (!usedQuestionIds.contains(q.id)) {
                questionList.add(q);
                usedQuestionIds.add(q.id);
            }
            if (questionList.size() >= 5) break;
        }

        if (questionList.isEmpty()) {
            questionList.addAll(rawQuestions);
        }

        quizProgressBar.setMax(questionList.size());
    }

    private void startQuizSession() {
        score = 0;
        streak = 0;
        maxStreak = 0;
        currentIndex = 0;
        lifelinesRemaining = 1;
        sessionReviewList.clear();

        btnQuizLifeline.setEnabled(true);
        btnQuizLifeline.setAlpha(1.0f);
        btnQuizLifeline.setText("💡 50:50 Hint");

        displayQuestion();
    }

    private void displayQuestion() {
        answered = false;
        cardQuizExplanation.setVisibility(View.GONE);
        btnQuizNext.setVisibility(View.GONE);

        resetOptionStyles();

        Question current = questionList.get(currentIndex);

        quizProgressBar.setProgress(currentIndex + 1);
        tvQuizProgressText.setText("Question " + (currentIndex + 1) + " of " + questionList.size());
        tvQuizStreak.setText("🔥 " + streak + " Streak");
        tvQuizDifficulty.setText(current.difficulty.toUpperCase());
        tvQuizTopic.setText("✦ " + current.topic);
        tvQuizQuestionText.setText(current.questionText);

        optText1.setText(current.optionA);
        optText2.setText(current.optionB);
        optText3.setText(current.optionC);
        optText4.setText(current.optionD);

        questionStartTime = System.currentTimeMillis();

        if (isPracticeMode) {
            tvQuizTimer.setText("🎯 Practice");
        } else {
            startTimer();
        }
    }

    private void startTimer() {
        if (countDownTimer != null) countDownTimer.cancel();

        countDownTimer = new CountDownTimer(QUESTION_TIMER_MILLIS, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int secondsLeft = (int) (millisUntilFinished / 1000);
                tvQuizTimer.setText(String.format("⏱ 00:%02d", secondsLeft));
            }

            @Override
            public void onFinish() {
                tvQuizTimer.setText("⏱ 00:00");
                onTimeUp();
            }
        }.start();
    }

    private void onTimeUp() {
        if (answered) return;
        answered = true;
        streak = 0;
        tvQuizStreak.setText("🔥 0 Streak");

        Question q = questionList.get(currentIndex);
        int correctIndex = getCorrectIndex(q.correctOption);
        highlightOption(correctIndex, true);

        long timeTaken = (System.currentTimeMillis() - questionStartTime) / 1000;
        totalTimeSpentMillis += (System.currentTimeMillis() - questionStartTime);
        sessionReviewList.add(new QuizSessionItem(q, "Timed Out", false, (int) timeTaken));

        showExplanation(false, "⏰ Time's up! " + q.explanation);
        btnQuizNext.setVisibility(View.VISIBLE);
    }

    private void checkAnswer(int selectedIndex) {
        answered = true;
        if (countDownTimer != null) countDownTimer.cancel();

        long timeTaken = (System.currentTimeMillis() - questionStartTime) / 1000;
        totalTimeSpentMillis += (System.currentTimeMillis() - questionStartTime);

        Question q = questionList.get(currentIndex);
        int correctIndex = getCorrectIndex(q.correctOption);

        String selectedLetter = getLetterForIndex(selectedIndex);
        boolean isCorrect = (selectedIndex == correctIndex);

        if (isCorrect) {
            score++;
            streak++;
            if (streak > maxStreak) maxStreak = streak;
            highlightOption(selectedIndex, true);
            showExplanation(true, "✅ Correct! " + q.explanation);
        } else {
            streak = 0;
            highlightOption(selectedIndex, false);
            highlightOption(correctIndex, true);
            showExplanation(false, "❌ Incorrect! " + q.explanation);
        }

        sessionReviewList.add(new QuizSessionItem(q, selectedLetter, isCorrect, (int) timeTaken));
        tvQuizStreak.setText("🔥 " + streak + " Streak");
        btnQuizNext.setVisibility(View.VISIBLE);
    }

    private int getCorrectIndex(String correctOption) {
        if ("B".equalsIgnoreCase(correctOption)) return 1;
        if ("C".equalsIgnoreCase(correctOption)) return 2;
        if ("D".equalsIgnoreCase(correctOption)) return 3;
        return 0;
    }

    private String getLetterForIndex(int idx) {
        if (idx == 1) return "B";
        if (idx == 2) return "C";
        if (idx == 3) return "D";
        return "A";
    }

    private void highlightOption(int index, boolean isCorrect) {
        LinearLayout card = getOptionCard(index);
        TextView badge = getOptionBadge(index);
        TextView text = getOptionText(index);
        TextView status = getOptionStatus(index);

        if (isCorrect) {
            card.setBackgroundResource(R.drawable.bg_option_correct);
            badge.setBackgroundResource(R.drawable.bg_badge_correct);
            badge.setTextColor(Color.WHITE);
            text.setTextColor(ContextCompat.getColor(this, R.color.correct_green_border));
            status.setVisibility(View.VISIBLE);
            status.setText("✔");
            status.setTextColor(ContextCompat.getColor(this, R.color.correct_green));
        } else {
            card.setBackgroundResource(R.drawable.bg_option_wrong);
            badge.setBackgroundResource(R.drawable.bg_badge_wrong);
            badge.setTextColor(Color.WHITE);
            text.setTextColor(ContextCompat.getColor(this, R.color.wrong_red_border));
            status.setVisibility(View.VISIBLE);
            status.setText("✖");
            status.setTextColor(ContextCompat.getColor(this, R.color.wrong_red));
        }
    }

    private LinearLayout getOptionCard(int index) {
        if (index == 1) return optCard2;
        if (index == 2) return optCard3;
        if (index == 3) return optCard4;
        return optCard1;
    }

    private TextView getOptionBadge(int index) {
        if (index == 1) return optBadge2;
        if (index == 2) return optBadge3;
        if (index == 3) return optBadge4;
        return optBadge1;
    }

    private TextView getOptionText(int index) {
        if (index == 1) return optText2;
        if (index == 2) return optText3;
        if (index == 3) return optText4;
        return optText1;
    }

    private TextView getOptionStatus(int index) {
        if (index == 1) return optStatus2;
        if (index == 2) return optStatus3;
        if (index == 3) return optStatus4;
        return optStatus1;
    }

    private void showExplanation(boolean isCorrect, String text) {
        cardQuizExplanation.setVisibility(View.VISIBLE);
        if (isCorrect) {
            cardQuizExplanation.setCardBackgroundColor(ContextCompat.getColor(this, R.color.correct_green_bg));
            cardQuizExplanation.setStrokeColor(ContextCompat.getColor(this, R.color.correct_green_border));
            tvQuizExpTitle.setTextColor(ContextCompat.getColor(this, R.color.correct_green_border));
            tvQuizExpTitle.setText("🎉 Awesome! Correct Explanation");
        } else {
            cardQuizExplanation.setCardBackgroundColor(ContextCompat.getColor(this, R.color.wrong_red_bg));
            cardQuizExplanation.setStrokeColor(ContextCompat.getColor(this, R.color.wrong_red_border));
            tvQuizExpTitle.setTextColor(ContextCompat.getColor(this, R.color.wrong_red_border));
            tvQuizExpTitle.setText("💡 Solution & Explanation");
        }
        tvQuizExpText.setText(text);
    }

    private void use5050Lifeline() {
        if (lifelinesRemaining <= 0 || answered) {
            Toast.makeText(this, "No lifelines remaining!", Toast.LENGTH_SHORT).show();
            return;
        }

        lifelinesRemaining--;
        btnQuizLifeline.setEnabled(false);
        btnQuizLifeline.setAlpha(0.4f);
        btnQuizLifeline.setText("💡 50:50 Used");

        Question q = questionList.get(currentIndex);
        int correctIdx = getCorrectIndex(q.correctOption);

        List<Integer> wrongIndices = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            if (i != correctIdx) wrongIndices.add(i);
        }
        Collections.shuffle(wrongIndices);

        disableOption(wrongIndices.get(0));
        disableOption(wrongIndices.get(1));

        Toast.makeText(this, "50:50 Used: 2 wrong answers removed!", Toast.LENGTH_SHORT).show();
    }

    private void disableOption(int index) {
        LinearLayout card = getOptionCard(index);
        card.setEnabled(false);
        card.setClickable(false);
        card.setAlpha(0.35f);
    }

    private void resetOptionStyles() {
        for (int i = 0; i < 4; i++) {
            LinearLayout card = getOptionCard(i);
            TextView badge = getOptionBadge(i);
            TextView text = getOptionText(i);
            TextView status = getOptionStatus(i);

            card.setEnabled(true);
            card.setClickable(true);
            card.setAlpha(1.0f);
            card.setBackgroundResource(R.drawable.bg_option_default);

            badge.setBackgroundResource(R.drawable.bg_badge_default);
            badge.setTextColor(ContextCompat.getColor(this, R.color.badge_default_text));

            text.setTextColor(ContextCompat.getColor(this, R.color.text_primary));
            status.setVisibility(View.GONE);
        }
    }

    private void finishQuizSession() {
        if (countDownTimer != null) countDownTimer.cancel();

        int totalQuestions = questionList.size();
        int accuracy = (int) (((double) score / totalQuestions) * 100);
        int timeSeconds = (int) (totalTimeSpentMillis / 1000);
        int pointsPerQuestion = "Hard".equalsIgnoreCase(difficulty) ? 30 : ("Medium".equalsIgnoreCase(difficulty) ? 20 : 10);
        int totalPointsEarned = score * pointsPerQuestion;

        // Persist to Room database & Prefs
        QuizHistory history = new QuizHistory(
                topic, difficulty, score, totalQuestions, accuracy, timeSeconds, totalPointsEarned, System.currentTimeMillis()
        );
        db.historyDao().insert(history);
        preferenceManager.addPoints(totalPointsEarned);
        preferenceManager.updateStreak();

        // Launch ResultActivity
        Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
        intent.putExtra("TOPIC", topic);
        intent.putExtra("DIFFICULTY", difficulty);
        intent.putExtra("SCORE", score);
        intent.putExtra("TOTAL_QUESTIONS", totalQuestions);
        intent.putExtra("ACCURACY", accuracy);
        intent.putExtra("TIME_SECONDS", timeSeconds);
        intent.putExtra("POINTS_EARNED", totalPointsEarned);
        intent.putExtra("MAX_STREAK", maxStreak);
        intent.putExtra("REVIEW_ITEMS", (Serializable) sessionReviewList);
        startActivity(intent);
        finish();
    }

    private void showExitConfirmation() {
        new AlertDialog.Builder(this)
                .setTitle("Exit Quiz?")
                .setMessage("Are you sure you want to exit? Your session progress will be lost.")
                .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (countDownTimer != null) countDownTimer.cancel();
                        finish();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    @Override
    public void onBackPressed() {
        showExitConfirmation();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) countDownTimer.cancel();
    }
}
