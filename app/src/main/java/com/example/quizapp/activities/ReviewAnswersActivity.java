package com.example.quizapp.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.quizapp.R;
import com.example.quizapp.adapters.ReviewAnswersAdapter;
import com.example.quizapp.models.QuizSessionItem;
import java.util.ArrayList;
import java.util.List;

public class ReviewAnswersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_answers);

        FrameLayout btnBackReview = findViewById(R.id.btnBackReview);
        TextView btnDoneReview = findViewById(R.id.btnDoneReview);
        RecyclerView rvReviewList = findViewById(R.id.rvReviewList);

        List<QuizSessionItem> reviewItems = (List<QuizSessionItem>) getIntent().getSerializableExtra("REVIEW_ITEMS");
        if (reviewItems == null) {
            reviewItems = new ArrayList<>();
        }

        rvReviewList.setLayoutManager(new LinearLayoutManager(this));
        ReviewAnswersAdapter adapter = new ReviewAnswersAdapter(this, reviewItems);
        rvReviewList.setAdapter(adapter);

        View.OnClickListener closeListener = v -> finish();
        btnBackReview.setOnClickListener(closeListener);
        btnDoneReview.setOnClickListener(closeListener);
    }
}
