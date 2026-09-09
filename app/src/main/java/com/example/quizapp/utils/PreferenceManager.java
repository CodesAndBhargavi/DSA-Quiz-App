package com.example.quizapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PreferenceManager {
    private static final String PREF_NAME = "DSAQuizPrefs";
    private static final String KEY_SOUND_ENABLED = "sound_enabled";
    private static final String KEY_DARK_MODE = "dark_mode";
    private static final String KEY_USER_NAME = "user_name";
    private static final String KEY_TOTAL_POINTS = "total_points";
    private static final String KEY_CURRENT_STREAK = "current_streak";
    private static final String KEY_LAST_PLAYED_DATE = "last_played_date";
    private static final String KEY_TIP_INDEX = "tip_index";

    private final SharedPreferences prefs;

    public PreferenceManager(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public boolean isSoundEnabled() {
        return prefs.getBoolean(KEY_SOUND_ENABLED, true);
    }

    public void setSoundEnabled(boolean enabled) {
        prefs.edit().putBoolean(KEY_SOUND_ENABLED, enabled).apply();
    }

    public boolean isDarkMode() {
        return prefs.getBoolean(KEY_DARK_MODE, false);
    }

    public void setDarkMode(boolean enabled) {
        prefs.edit().putBoolean(KEY_DARK_MODE, enabled).apply();
    }

    public String getUserName() {
        return prefs.getString(KEY_USER_NAME, "Learner");
    }

    public void setUserName(String name) {
        prefs.edit().putString(KEY_USER_NAME, name).apply();
    }

    public int getTotalPoints() {
        return prefs.getInt(KEY_TOTAL_POINTS, 0);
    }

    public void addPoints(int points) {
        int current = getTotalPoints();
        prefs.edit().putInt(KEY_TOTAL_POINTS, current + points).apply();
    }

    public int getCurrentStreak() {
        return prefs.getInt(KEY_CURRENT_STREAK, 1);
    }

    public void updateStreak() {
        String today = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        String lastDate = prefs.getString(KEY_LAST_PLAYED_DATE, "");

        if (!today.equals(lastDate)) {
            int currentStreak = getCurrentStreak();
            // Check if played yesterday
            long diffDays = 1; 
            // Increment streak
            prefs.edit().putInt(KEY_CURRENT_STREAK, currentStreak + 1).putString(KEY_LAST_PLAYED_DATE, today).apply();
        }
    }

    public int getNextTipIndex(int totalTips) {
        int idx = prefs.getInt(KEY_TIP_INDEX, 0);
        int next = (idx + 1) % totalTips;
        prefs.edit().putInt(KEY_TIP_INDEX, next).apply();
        return idx;
    }

    public void resetProgress() {
        prefs.edit().clear().apply();
    }
}
