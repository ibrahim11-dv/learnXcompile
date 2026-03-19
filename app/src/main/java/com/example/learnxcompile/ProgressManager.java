package com.example.learnxcompile;

import android.content.Context;
import android.content.SharedPreferences;

public class ProgressManager {
    private static final String PREF_NAME = "LearningProgress";
    private static final String KEY_JAVA_PROGRESS = "java_progress";
    private static final String KEY_PYTHON_PROGRESS = "python_progress";
    private static final String KEY_CPP_PROGRESS = "cpp_progress";

    private SharedPreferences prefs;

    public ProgressManager(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    // Sauvegarder la progression pour un langage
    public void setProgress(String language, int progress) {
        SharedPreferences.Editor editor = prefs.edit();
        switch(language) {
            case "Java":
                editor.putInt(KEY_JAVA_PROGRESS, progress);
                break;
            case "Python":
                editor.putInt(KEY_PYTHON_PROGRESS, progress);
                break;
            case "C++":
                editor.putInt(KEY_CPP_PROGRESS, progress);
                break;
        }
        editor.apply();
    }

    // Récupérer la progression
    public int getProgress(String language) {
        switch(language) {
            case "Java":
                return prefs.getInt(KEY_JAVA_PROGRESS, 0);
            case "Python":
                return prefs.getInt(KEY_PYTHON_PROGRESS, 0);
            case "C++":
                return prefs.getInt(KEY_CPP_PROGRESS, 0);
            default:
                return 0;
        }
    }

    // Récupérer le nombre total de chapitres
    public int getTotalChapters(String language) {
        switch(language) {
            case "Java":
                return 4;
            case "Python":
                return 8;
            case "C++":
                return 8;
            default:
                return 0;
        }
    }
}