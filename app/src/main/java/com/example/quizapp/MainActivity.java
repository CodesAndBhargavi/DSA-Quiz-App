package com.example.quizapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.google.android.material.card.MaterialCardView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    static class Question {
        String question;
        String[] options;
        int correctIndex;
        String explanation;
        String difficulty;

        Question(String q, String[] opts, int correct, String exp, String diff) {
            this.question = q;
            this.options = opts;
            this.correctIndex = correct;
            this.explanation = exp;
            this.difficulty = diff;
        }
    }

    // Top Bar & Info
    private View btnBack, btnSkip, layoutTopBar;
    private TextView tvCategory, tvStreak, tvProgress, tvTimer, tvDifficulty, tvQuestion;
    private ProgressBar timerProgressBar;

    // Main Card & Options
    private LinearLayout cardMainQuiz, layoutBottomControls;
    private LinearLayout cardOpt1, cardOpt2, cardOpt3, cardOpt4;
    private TextView tvBadge1, tvBadge2, tvBadge3, tvBadge4;
    private TextView tvOpt1, tvOpt2, tvOpt3, tvOpt4;
    private TextView ivStatus1, ivStatus2, ivStatus3, ivStatus4;

    // Action Controls & Explanation
    private TextView btnLifeline, btnNext;
    private MaterialCardView cardExplanation;
    private TextView tvExplanationTitle, tvExplanationText;

    // Results Screen
    private LinearLayout layoutResult;
    private TextView tvFinalBadge, tvFinalTitle, tvFinalScore, tvHighScore, tvMaxStreak;
    private TextView btnRestart;

    // State Variables
    private List<Question> questionList;
    private int currentIndex = 0;
    private int score = 0;
    private int streak = 0;
    private int maxStreak = 0;
    private int lifelinesRemaining = 1;
    private boolean answered = false;

    private CountDownTimer countDownTimer;
    private static final long COUNTDOWN_IN_MILLIS = 15000;
    private long timeLeftInMillis;

    private SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "QuizAppPrefs";
    private static final String KEY_HIGH_SCORE = "high_score";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        initViews();
        setupQuestions();
        startQuiz();
    }

    private void initViews() {
        // Top Bar
        layoutTopBar = findViewById(R.id.layoutTopBar);
        btnBack = findViewById(R.id.btnBack);
        btnSkip = findViewById(R.id.btnSkip);
        tvProgress = findViewById(R.id.tvProgress);
        tvTimer = findViewById(R.id.tvTimer);
        timerProgressBar = findViewById(R.id.timerProgressBar);

        // Header
        tvCategory = findViewById(R.id.tvCategory);
        tvDifficulty = findViewById(R.id.tvDifficulty);
        tvStreak = findViewById(R.id.tvStreak);
        tvQuestion = findViewById(R.id.tvQuestion);

        // Main Quiz Container
        cardMainQuiz = findViewById(R.id.cardMainQuiz);
        layoutBottomControls = findViewById(R.id.layoutBottomControls);

        // Options Containers
        cardOpt1 = findViewById(R.id.cardOpt1);
        cardOpt2 = findViewById(R.id.cardOpt2);
        cardOpt3 = findViewById(R.id.cardOpt3);
        cardOpt4 = findViewById(R.id.cardOpt4);

        // Option Badges
        tvBadge1 = findViewById(R.id.tvBadge1);
        tvBadge2 = findViewById(R.id.tvBadge2);
        tvBadge3 = findViewById(R.id.tvBadge3);
        tvBadge4 = findViewById(R.id.tvBadge4);

        // Option Texts
        tvOpt1 = findViewById(R.id.tvOpt1);
        tvOpt2 = findViewById(R.id.tvOpt2);
        tvOpt3 = findViewById(R.id.tvOpt3);
        tvOpt4 = findViewById(R.id.tvOpt4);

        // Status Icons
        ivStatus1 = findViewById(R.id.ivStatus1);
        ivStatus2 = findViewById(R.id.ivStatus2);
        ivStatus3 = findViewById(R.id.ivStatus3);
        ivStatus4 = findViewById(R.id.ivStatus4);

        // Actions & Explanation
        btnLifeline = findViewById(R.id.btnLifeline);
        btnNext = findViewById(R.id.btnNext);
        cardExplanation = findViewById(R.id.cardExplanation);
        tvExplanationTitle = findViewById(R.id.tvExplanationTitle);
        tvExplanationText = findViewById(R.id.tvExplanationText);

        // Results
        layoutResult = findViewById(R.id.layoutResult);
        tvFinalBadge = findViewById(R.id.tvFinalBadge);
        tvFinalTitle = findViewById(R.id.tvFinalTitle);
        tvFinalScore = findViewById(R.id.tvFinalScore);
        tvHighScore = findViewById(R.id.tvHighScore);
        tvMaxStreak = findViewById(R.id.tvMaxStreak);
        btnRestart = findViewById(R.id.btnRestart);

        // Click Listeners for Options
        View.OnClickListener optionClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!answered) {
                    int selectedIndex = 0;
                    if (v.getId() == R.id.cardOpt2) selectedIndex = 1;
                    else if (v.getId() == R.id.cardOpt3) selectedIndex = 2;
                    else if (v.getId() == R.id.cardOpt4) selectedIndex = 3;

                    checkAnswer(selectedIndex);
                }
            }
        };

        cardOpt1.setOnClickListener(optionClickListener);
        cardOpt2.setOnClickListener(optionClickListener);
        cardOpt3.setOnClickListener(optionClickListener);
        cardOpt4.setOnClickListener(optionClickListener);

        // Next / Continue Button
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentIndex++;
                if (currentIndex < questionList.size()) {
                    displayQuestion();
                } else {
                    showFinalResults();
                }
            }
        });

        // 50:50 Lifeline Button
        btnLifeline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                use5050Lifeline();
            }
        });

        // Skip Button
        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!answered) {
                    onTimeUp();
                }
            }
        });

        // Back Button
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startQuiz();
            }
        });

        // Restart Button
        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startQuiz();
            }
        });
    }

    private void setupQuestions() {
        questionList = new ArrayList<>();
        questionList.add(new Question(
                "Which component in Android is responsible for presenting a single screen with a user interface?",
                new String[]{"Activity", "Service", "BroadcastReceiver", "ContentProvider"},
                0,
                "An Activity is a single, focused thing that the user can do, representing one UI window in Android.",
                "BEGINNER"
        ));

        questionList.add(new Question(
                "What is the time complexity of searching an element in a balanced Binary Search Tree (BST)?",
                new String[]{"O(log n)", "O(n)", "O(1)", "O(n log n)"},
                0,
                "In a balanced BST, each comparison cuts the search space in half, resulting in O(log n) time.",
                "INTERMEDIATE"
        ));

        questionList.add(new Question(
                "Which keyword in Java prevents a class from being inherited/subclassed?",
                new String[]{"final", "static", "abstract", "const"},
                0,
                "The 'final' keyword on a class prevents other classes from extending it.",
                "BEGINNER"
        ));

        questionList.add(new Question(
                "Why is RecyclerView preferred over ListView in modern Android development?",
                new String[]{"Recycles view holders for smoother memory & scrolling", "It is written in Python", "It does not need an adapter", "It cannot display images"},
                0,
                "RecyclerView reuses existing view items (ViewHolder pattern), making it highly memory-efficient.",
                "INTERMEDIATE"
        ));

        questionList.add(new Question(
                "Which HTTP response code indicates that a requested resource was not found on the server?",
                new String[]{"404 Not Found", "200 OK", "500 Internal Error", "301 Moved Permanently"},
                0,
                "HTTP 404 indicates the client can communicate with the server, but the requested resource doesn't exist.",
                "EASY"
        ));
    }

    private void startQuiz() {
        score = 0;
        streak = 0;
        maxStreak = 0;
        currentIndex = 0;
        lifelinesRemaining = 1;

        btnLifeline.setEnabled(true);
        btnLifeline.setAlpha(1.0f);
        btnLifeline.setText("💡 50:50 Hint");

        cardMainQuiz.setVisibility(View.VISIBLE);
        layoutBottomControls.setVisibility(View.VISIBLE);
        layoutTopBar.setVisibility(View.VISIBLE);
        layoutResult.setVisibility(View.GONE);

        displayQuestion();
    }

    private void displayQuestion() {
        answered = false;
        cardExplanation.setVisibility(View.GONE);
        btnNext.setVisibility(View.GONE);

        resetOptionStyles();

        Question current = questionList.get(currentIndex);

        tvProgress.setText("Question " + (currentIndex + 1) + " of " + questionList.size());
        tvStreak.setText("🔥 " + streak + " Streak");
        tvDifficulty.setText(current.difficulty);
        tvQuestion.setText(current.question);

        tvOpt1.setText(current.options[0]);
        tvOpt2.setText(current.options[1]);
        tvOpt3.setText(current.options[2]);
        tvOpt4.setText(current.options[3]);

        startTimer();
    }

    private void startTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        timeLeftInMillis = COUNTDOWN_IN_MILLIS;
        timerProgressBar.setMax(15);
        timerProgressBar.setProgress(15);

        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                int secondsLeft = (int) (millisUntilFinished / 1000);
                tvTimer.setText("⏱ " + secondsLeft + "s");
                timerProgressBar.setProgress(secondsLeft);
            }

            @Override
            public void onFinish() {
                tvTimer.setText("⏱ 0s");
                timerProgressBar.setProgress(0);
                onTimeUp();
            }
        }.start();
    }

    private void onTimeUp() {
        if (answered) return;
        answered = true;
        if (countDownTimer != null) countDownTimer.cancel();

        streak = 0;
        tvStreak.setText("🔥 0 Streak");

        Question q = questionList.get(currentIndex);
        highlightCorrectOption(q.correctIndex);

        showExplanation(false, "⏰ Time's up! " + q.explanation);
        btnNext.setVisibility(View.VISIBLE);
    }

    private void checkAnswer(int selectedIndex) {
        answered = true;
        if (countDownTimer != null) countDownTimer.cancel();

        Question q = questionList.get(currentIndex);

        if (selectedIndex == q.correctIndex) {
            score++;
            streak++;
            if (streak > maxStreak) maxStreak = streak;

            setOptionStyle(selectedIndex, true);
            showExplanation(true, "✅ Correct! " + q.explanation);
        } else {
            streak = 0;
            setOptionStyle(selectedIndex, false);
            highlightCorrectOption(q.correctIndex);
            showExplanation(false, "❌ Incorrect! " + q.explanation);
        }

        tvStreak.setText("🔥 " + streak + " Streak");
        btnNext.setVisibility(View.VISIBLE);
    }

    private void setOptionStyle(int index, boolean isCorrect) {
        LinearLayout card = getOptionCard(index);
        TextView badge = getOptionBadge(index);
        TextView optText = getOptionText(index);
        TextView status = getOptionStatus(index);

        if (isCorrect) {
            card.setBackgroundResource(R.drawable.bg_option_correct);
            badge.setBackgroundResource(R.drawable.bg_badge_correct);
            badge.setTextColor(Color.WHITE);
            optText.setTextColor(ContextCompat.getColor(this, R.color.correct_green_border));
            status.setVisibility(View.VISIBLE);
            status.setText("✔");
            status.setTextColor(ContextCompat.getColor(this, R.color.correct_green));
        } else {
            card.setBackgroundResource(R.drawable.bg_option_wrong);
            badge.setBackgroundResource(R.drawable.bg_badge_wrong);
            badge.setTextColor(Color.WHITE);
            optText.setTextColor(ContextCompat.getColor(this, R.color.wrong_red_border));
            status.setVisibility(View.VISIBLE);
            status.setText("✖");
            status.setTextColor(ContextCompat.getColor(this, R.color.wrong_red));
        }
    }

    private void highlightCorrectOption(int correctIndex) {
        setOptionStyle(correctIndex, true);
    }

    private LinearLayout getOptionCard(int index) {
        if (index == 1) return cardOpt2;
        if (index == 2) return cardOpt3;
        if (index == 3) return cardOpt4;
        return cardOpt1;
    }

    private TextView getOptionBadge(int index) {
        if (index == 1) return tvBadge2;
        if (index == 2) return tvBadge3;
        if (index == 3) return tvBadge4;
        return tvBadge1;
    }

    private TextView getOptionText(int index) {
        if (index == 1) return tvOpt2;
        if (index == 2) return tvOpt3;
        if (index == 3) return tvOpt4;
        return tvOpt1;
    }

    private TextView getOptionStatus(int index) {
        if (index == 1) return ivStatus2;
        if (index == 2) return ivStatus3;
        if (index == 3) return ivStatus4;
        return ivStatus1;
    }

    private void showExplanation(boolean isCorrect, String text) {
        cardExplanation.setVisibility(View.VISIBLE);
        if (isCorrect) {
            cardExplanation.setCardBackgroundColor(ContextCompat.getColor(this, R.color.correct_green_bg));
            cardExplanation.setStrokeColor(ContextCompat.getColor(this, R.color.correct_green_border));
            tvExplanationTitle.setTextColor(ContextCompat.getColor(this, R.color.correct_green_border));
            tvExplanationTitle.setText("🎉 Awesome! Correct Explanation");
        } else {
            cardExplanation.setCardBackgroundColor(ContextCompat.getColor(this, R.color.wrong_red_bg));
            cardExplanation.setStrokeColor(ContextCompat.getColor(this, R.color.wrong_red_border));
            tvExplanationTitle.setTextColor(ContextCompat.getColor(this, R.color.wrong_red_border));
            tvExplanationTitle.setText("💡 Solution & Explanation");
        }
        tvExplanationText.setText(text);
    }

    private void use5050Lifeline() {
        if (lifelinesRemaining <= 0 || answered) {
            Toast.makeText(this, "No lifelines remaining!", Toast.LENGTH_SHORT).show();
            return;
        }

        lifelinesRemaining--;
        btnLifeline.setEnabled(false);
        btnLifeline.setAlpha(0.5f);
        btnLifeline.setText("💡 50:50 Used");

        Question q = questionList.get(currentIndex);
        List<Integer> wrongIndices = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            if (i != q.correctIndex) wrongIndices.add(i);
        }
        Collections.shuffle(wrongIndices);

        disableOption(wrongIndices.get(0));
        disableOption(wrongIndices.get(1));

        Toast.makeText(this, "50:50 Used: 2 incorrect options removed!", Toast.LENGTH_SHORT).show();
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
            TextView optText = getOptionText(i);
            TextView status = getOptionStatus(i);

            card.setEnabled(true);
            card.setClickable(true);
            card.setAlpha(1.0f);
            card.setBackgroundResource(R.drawable.bg_option_default);

            badge.setBackgroundResource(R.drawable.bg_badge_default);
            badge.setTextColor(ContextCompat.getColor(this, R.color.badge_default_text));

            optText.setTextColor(ContextCompat.getColor(this, R.color.text_primary));
            status.setVisibility(View.GONE);
        }
    }

    private void showFinalResults() {
        if (countDownTimer != null) countDownTimer.cancel();

        cardMainQuiz.setVisibility(View.GONE);
        layoutBottomControls.setVisibility(View.GONE);
        cardExplanation.setVisibility(View.GONE);
        layoutTopBar.setVisibility(View.GONE);

        layoutResult.setVisibility(View.VISIBLE);

        int oldHighScore = sharedPreferences.getInt(KEY_HIGH_SCORE, 0);
        if (score > oldHighScore) {
            sharedPreferences.edit().putInt(KEY_HIGH_SCORE, score).apply();
            oldHighScore = score;
        }

        double percentage = ((double) score / questionList.size()) * 100;
        tvFinalScore.setText("Your Score: " + score + " / " + questionList.size() + " (" + (int) percentage + "%)");
        tvHighScore.setText(String.valueOf(oldHighScore));
        tvMaxStreak.setText(String.valueOf(maxStreak));

        if (score == questionList.size()) {
            tvFinalBadge.setText("🏆");
            tvFinalTitle.setText("Grandmaster Coder!");
        } else if (score >= 3) {
            tvFinalBadge.setText("🥈");
            tvFinalTitle.setText("Great Performance!");
        } else {
            tvFinalBadge.setText("📚");
            tvFinalTitle.setText("Keep Practicing!");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) countDownTimer.cancel();
    }
}