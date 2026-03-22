package com.example.learnxcompile;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.DialogInterface;
import androidx.appcompat.app.AlertDialog;
import android.content.SharedPreferences;
import androidx.cardview.widget.CardView;
import android.widget.TextView;
import android.widget.ProgressBar;

import com.example.learnxcompile.Controllers.ChapterController;
import com.example.learnxcompile.Items.Language;

import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private TextView tvJavaProgress, tvPythonProgress;
    private ProgressBar progressJava, progressPython;
    private TextView tvLessonsCount;
    private ChapterController chapterController;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        chapterController = new ChapterController(this);
        prefs = getSharedPreferences("SubscriptionPrefs", MODE_PRIVATE);

        initViews();
        loadData();
        setupClickListeners();
    }

    private void initViews() {
        tvJavaProgress = findViewById(R.id.tvJavaProgress);
        tvPythonProgress = findViewById(R.id.tvPythonProgress);
        progressJava = findViewById(R.id.progressJava);
        progressPython = findViewById(R.id.progressPython);
        tvLessonsCount = findViewById(R.id.tvLessonsCount);
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
            }
        }
        tvLessonsCount.setText(String.valueOf(totalDone));
    }

    private void setupClickListeners() {
        findViewById(R.id.cardJava).setOnClickListener(v -> checkSubscriptionAndOpen("Java"));
        findViewById(R.id.cardPython).setOnClickListener(v -> checkSubscriptionAndOpen("Python"));
    }

    private void checkSubscriptionAndOpen(String languageName) {
        SharedPreferences prefs = getSharedPreferences("SubscriptionPrefs", MODE_PRIVATE);
        boolean isSubscribed = prefs.getBoolean("subscribed_" + languageName, false);

        if (isSubscribed) {
            startChapters(languageName);
        } else {
            showSubscriptionDialog(languageName);
        }
    }

    private void showSubscriptionDialog(String languageName) {
        new AlertDialog.Builder(this)
                .setTitle("Abonnement requis")
                .setMessage("Vous devez vous abonner (5$) pour accéder au cours " + languageName + ". Voulez-vous continuer ?")
                .setPositiveButton("S'abonner", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(HomeActivity.this, SubscriptionActivity.class);
                        intent.putExtra("LANGUAGE_NAME", languageName);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Non merci", null)
                .show();
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