package com.example.quizapp.activities;

import android.os.Bundle;
import android.widget.FrameLayout;
import androidx.appcompat.app.AppCompatActivity;
import com.example.quizapp.R;

public class StudyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study);

        FrameLayout btnBackStudy = findViewById(R.id.btnBackStudy);
        btnBackStudy.setOnClickListener(v -> finish());
    }
}
