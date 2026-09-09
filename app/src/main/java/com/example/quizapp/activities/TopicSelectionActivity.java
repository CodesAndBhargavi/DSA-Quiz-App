package com.example.quizapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.example.quizapp.R;
import com.google.android.material.chip.ChipGroup;

public class TopicSelectionActivity extends AppCompatActivity {

    private boolean isPracticeMode = false;
    private String selectedTopic = "All";
    private String selectedDifficulty = "Basic";

    private FrameLayout btnBackTopic;
    private TextView tvModeTitle, tvQuestionsAvailable, tvTimeEstimate, btnStartSelectedQuiz;
    private ChipGroup chipGroupTopics;
    private LinearLayout cardDiffBasic, cardDiffMedium, cardDiffHard;
    private TextView tvDiffBasicTitle, tvDiffMediumTitle, tvDiffHardTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_selection);

        isPracticeMode = getIntent().getBooleanExtra("IS_PRACTICE_MODE", false);

        initViews();
        setupListeners();
        updateUI();
    }

    private void initViews() {
        btnBackTopic = findViewById(R.id.btnBackTopic);
        tvModeTitle = findViewById(R.id.tvModeTitle);
        tvQuestionsAvailable = findViewById(R.id.tvQuestionsAvailable);
        tvTimeEstimate = findViewById(R.id.tvTimeEstimate);
        btnStartSelectedQuiz = findViewById(R.id.btnStartSelectedQuiz);

        chipGroupTopics = findViewById(R.id.chipGroupTopics);
        cardDiffBasic = findViewById(R.id.cardDiffBasic);
        cardDiffMedium = findViewById(R.id.cardDiffMedium);
        cardDiffHard = findViewById(R.id.cardDiffHard);

        tvDiffBasicTitle = findViewById(R.id.tvDiffBasicTitle);
        tvDiffMediumTitle = findViewById(R.id.tvDiffMediumTitle);
        tvDiffHardTitle = findViewById(R.id.tvDiffHardTitle);
    }

    private void updateUI() {
        if (isPracticeMode) {
            tvModeTitle.setText("Training / Practice Mode");
            btnStartSelectedQuiz.setText("Start Practice 🎯");
            tvTimeEstimate.setText("Untimed • Instant explanations • No pressure");
        } else {
            tvModeTitle.setText("Timed Quiz Challenge");
            btnStartSelectedQuiz.setText("Start Challenge 🚀");
            tvTimeEstimate.setText("30s per question • Stars & accuracy scored");
        }
    }

    private void setupListeners() {
        btnBackTopic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Topic chips selection
        chipGroupTopics.setOnCheckedStateChangeListener(new ChipGroup.OnCheckedStateChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, java.util.List<Integer> checkedIds) {
                if (checkedIds.isEmpty()) return;
                int id = checkedIds.get(0);
                if (id == R.id.chipArrays) selectedTopic = "Arrays";
                else if (id == R.id.chipLinkedList) selectedTopic = "LinkedList";
                else if (id == R.id.chipStacks) selectedTopic = "StacksQueues";
                else if (id == R.id.chipTrees) selectedTopic = "Trees";
                else if (id == R.id.chipGraphs) selectedTopic = "Graphs";
                else if (id == R.id.chipSorting) selectedTopic = "Sorting";
                else if (id == R.id.chipDP) selectedTopic = "DP";
                else if (id == R.id.chipComplexity) selectedTopic = "TimeComplexity";
                else selectedTopic = "All";
            }
        });

        // Difficulty card clicks
        cardDiffBasic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDifficulty("Basic");
            }
        });

        cardDiffMedium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDifficulty("Medium");
            }
        });

        cardDiffHard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDifficulty("Hard");
            }
        });

        // Start Quiz Action
        btnStartSelectedQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TopicSelectionActivity.this, QuizActivity.class);
                intent.putExtra("TOPIC", selectedTopic);
                intent.putExtra("DIFFICULTY", selectedDifficulty);
                intent.putExtra("IS_PRACTICE_MODE", isPracticeMode);
                startActivity(intent);
                finish();
            }
        });
    }

    private void selectDifficulty(String diff) {
        selectedDifficulty = diff;

        cardDiffBasic.setBackgroundResource(R.drawable.bg_option_default);
        cardDiffMedium.setBackgroundResource(R.drawable.bg_option_default);
        cardDiffHard.setBackgroundResource(R.drawable.bg_option_default);

        tvDiffBasicTitle.setTextColor(ContextCompat.getColor(this, R.color.text_primary));
        tvDiffMediumTitle.setTextColor(ContextCompat.getColor(this, R.color.text_primary));
        tvDiffHardTitle.setTextColor(ContextCompat.getColor(this, R.color.text_primary));

        if ("Basic".equals(diff)) {
            cardDiffBasic.setBackgroundResource(R.drawable.bg_option_correct);
            tvDiffBasicTitle.setTextColor(ContextCompat.getColor(this, R.color.diff_basic_text));
        } else if ("Medium".equals(diff)) {
            cardDiffMedium.setBackgroundResource(R.drawable.bg_option_correct);
            tvDiffMediumTitle.setTextColor(ContextCompat.getColor(this, R.color.diff_medium_text));
        } else if ("Hard".equals(diff)) {
            cardDiffHard.setBackgroundResource(R.drawable.bg_option_correct);
            tvDiffHardTitle.setTextColor(ContextCompat.getColor(this, R.color.diff_hard_text));
        }
    }
}
