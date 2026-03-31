package com.example.learnxcompile;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import androidx.appcompat.app.AlertDialog;
import android.content.SharedPreferences;
import android.widget.TextView;
import android.widget.ProgressBar;

import com.example.learnxcompile.Controllers.ChapterController;
import com.example.learnxcompile.Items.Language;

import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private TextView tvJavaProgress, tvPythonProgress, tvCppProgress;
    private ProgressBar progressJava, progressPython, progressCpp;
    private TextView tvLessonsCount, tvUsername, tvAvatar;
    private ChapterController chapterController;
    private SharedPreferences subscriptionPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        chapterController = new ChapterController(this);
        subscriptionPrefs = getSharedPreferences("SubscriptionPrefs", MODE_PRIVATE);

        initViews();
        loadUserInfo();
        loadData();
        setupClickListeners();
        setupNavigation();
    }

    private void initViews() {
        tvJavaProgress = findViewById(R.id.tvJavaProgress);
        tvPythonProgress = findViewById(R.id.tvPythonProgress);
        tvCppProgress = findViewById(R.id.tvCppProgress);
        progressJava = findViewById(R.id.progressJava);
        progressPython = findViewById(R.id.progressPython);
        progressCpp = findViewById(R.id.progressCpp);
        tvLessonsCount = findViewById(R.id.tvLessonsCount);
        tvUsername = findViewById(R.id.tvUsername);
        tvAvatar = findViewById(R.id.tvAvatar);
    }

    private void loadUserInfo() {
        SharedPreferences userPrefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String username = userPrefs.getString("username", "User");
        tvUsername.setText(username);
        
        if (username != null && !username.isEmpty()) {
            if (username.length() >= 2) {
                tvAvatar.setText(username.substring(0, 2).toUpperCase());
            } else {
                tvAvatar.setText(username.toUpperCase());
            }
        }
    }

    private void loadData() {
        List<Language> languages = chapterController.getLanguages();
        int totalDone = 0;

        for (Language lang : languages) {
            totalDone += lang.chapterCompleted;
            if (lang.name.equalsIgnoreCase("Java")) {
                tvJavaProgress.setText(lang.chapterCompleted + " / " + lang.chapterCount + " lessons");
                progressJava.setProgress(lang.progress);
            } else if (lang.name.equalsIgnoreCase("Python")) {
                tvPythonProgress.setText(lang.chapterCompleted + " / " + lang.chapterCount + " lessons");
                progressPython.setProgress(lang.progress);
            } else if (lang.name.equalsIgnoreCase("C++")) {
                tvCppProgress.setText(lang.chapterCompleted + " / " + lang.chapterCount + " lessons");
                progressCpp.setProgress(lang.progress);
            }
        }
        tvLessonsCount.setText(String.valueOf(totalDone));
    }

    private void setupClickListeners() {
        findViewById(R.id.cardJava).setOnClickListener(v -> checkSubscriptionAndOpen("Java"));
        findViewById(R.id.cardPython).setOnClickListener(v -> checkSubscriptionAndOpen("Python"));
        findViewById(R.id.cardCpp).setOnClickListener(v -> checkSubscriptionAndOpen("C++"));
    }

    private void setupNavigation() {
        findViewById(R.id.navProfile).setOnClickListener(v -> {
            startActivity(new Intent(this, ProfileActivity.class));
            overridePendingTransition(0, 0);
        });
    }

    private void checkSubscriptionAndOpen(String languageName) {
        boolean allSubscribed = subscriptionPrefs.getBoolean("all_courses_subscribed", false);
        boolean isSubscribed = subscriptionPrefs.getBoolean("subscribed_" + languageName, false);

        if (allSubscribed || isSubscribed) {
            startChapters(languageName);
        } else {
            Language lang = chapterController.getLanguageByName(languageName);
            if (lang != null && lang.chapterCompleted >= 4) {
                 showSubscriptionPopUp(languageName);
            } else {
                 startChapters(languageName);
            }
        }
    }

    private void showSubscriptionPopUp(String languageName) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_subscription_options, null);
        builder.setView(view);

        AlertDialog dialog = builder.create();
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }

        TextView tvTitle = view.findViewById(R.id.tvCourseTitle);
        tvTitle.setText("Cours " + languageName + " uniquement");

        view.findViewById(R.id.cardSingleCourse).setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, SubscriptionActivity.class);
            intent.putExtra("LANGUAGE_NAME", languageName);
            intent.putExtra("PLAN_TYPE", "SINGLE");
            startActivity(intent);
            dialog.dismiss();
        });

        view.findViewById(R.id.cardAllCourses).setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, SubscriptionActivity.class);
            intent.putExtra("LANGUAGE_NAME", "ALL");
            intent.putExtra("PLAN_TYPE", "ALL_COURSES");
            startActivity(intent);
            dialog.dismiss();
        });

        view.findViewById(R.id.btnCancel).setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    private void startChapters(String languageName) {
        Intent intent = new Intent(HomeActivity.this, ChaptersActivity.class);
        intent.putExtra("LANGUAGE_NAME", languageName);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }
}
