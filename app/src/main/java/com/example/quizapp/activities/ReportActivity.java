package com.example.quizapp.activities;

import android.os.Bundle;
import android.widget.FrameLayout;
import androidx.appcompat.app.AppCompatActivity;
import com.example.quizapp.R;

public class ReportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        FrameLayout btnBackReport = findViewById(R.id.btnBackReport);
        btnBackReport.setOnClickListener(v -> finish());
    }
}
