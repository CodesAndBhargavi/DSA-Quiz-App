package com.example.quizapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.quizapp.R;
import com.example.quizapp.database.DataSeeder;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Pre-seed Room database in background/main
        DataSeeder.seedDatabaseIfEmpty(this);

        TextView btnStartQuiz = findViewById(R.id.btnStartQuiz);
        TextView btnSkipSplash = findViewById(R.id.btnSkipSplash);

        View.OnClickListener startHomeListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        };

        btnStartQuiz.setOnClickListener(startHomeListener);
        btnSkipSplash.setOnClickListener(startHomeListener);
    }
}
