package com.example.quizapp.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.quizapp.R;
import com.example.quizapp.database.AppDatabase;
import com.example.quizapp.utils.PreferenceManager;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        FrameLayout btnBackSettings = findViewById(R.id.btnBackSettings);
        LinearLayout btnResetProgress = findViewById(R.id.btnResetProgress);

        btnBackSettings.setOnClickListener(v -> finish());

        btnResetProgress.setOnClickListener(v -> {
            new AlertDialog.Builder(SettingsActivity.this)
                    .setTitle("Reset All Progress?")
                    .setMessage("This will clear your total stars, streaks, and quiz history. This action cannot be undone.")
                    .setPositiveButton("Reset", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            AppDatabase.getInstance(SettingsActivity.this).historyDao().clearHistory();
                            new PreferenceManager(SettingsActivity.this).resetProgress();
                            Toast.makeText(SettingsActivity.this, "Progress has been reset!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });
    }
}
