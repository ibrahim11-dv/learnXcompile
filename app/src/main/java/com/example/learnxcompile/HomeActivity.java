package com.example.learnxcompile;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import androidx.cardview.widget.CardView;
import android.widget.TextView;
import android.widget.ProgressBar;

import com.example.learnxcompile.Items.Language;
import com.example.learnxcompile.Models.ChapterModel;

import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private TextView tvJavaProgress, tvPythonProgress;
    private ProgressBar progressJava, progressPython;
    private TextView tvLessonsCount;
    private ChapterModel chapterModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        chapterModel = new ChapterModel(this);
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
        List<Language> languages = chapterModel.getLanguages();
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
        findViewById(R.id.cardJava).setOnClickListener(v -> startChapters("Java"));
        findViewById(R.id.cardPython).setOnClickListener(v -> startChapters("Python"));
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
