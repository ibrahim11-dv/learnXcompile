package com.example.learnxcompile;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import androidx.cardview.widget.CardView;
import android.widget.TextView;
import android.widget.ProgressBar;

// IMPORTANT : importer depuis Controllers
import com.example.learnxcompile.Controllers.ChaptersActivity;
import com.example.learnxcompile.ProgressManager;

public class HomeActivity extends AppCompatActivity {

    private ProgressManager progressManager;

    // Views pour la progression
    private TextView tvJavaProgress;
    private TextView tvPythonProgress;
    private TextView tvCppProgress;
    private ProgressBar progressJava;
    private ProgressBar progressPython;
    private ProgressBar progressCpp;
    private TextView tvLessonsCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Initialiser le gestionnaire de progression
        progressManager = new ProgressManager(this);

        // Initialiser les vues
        initViews();

        // Mettre à jour l'affichage de la progression
        updateProgressDisplay();

        // Configurer les clics sur les cartes
        setupClickListeners();
    }

    private void initViews() {
        // Textes de progression
        tvJavaProgress = findViewById(R.id.tvJavaProgress);
        tvPythonProgress = findViewById(R.id.tvPythonProgress);
        tvCppProgress = findViewById(R.id.tvCppProgress);

        // Barres de progression
        progressJava = findViewById(R.id.progressJava);
        progressPython = findViewById(R.id.progressPython);
        progressCpp = findViewById(R.id.progressCpp);

        // Compteur total de leçons
        tvLessonsCount = findViewById(R.id.tvLessonsCount);
    }

    private void updateProgressDisplay() {
        // Java (4 chapitres)
        int javaProgress = progressManager.getProgress("Java");
        tvJavaProgress.setText(javaProgress + " / 4 lessons");
        progressJava.setProgress(javaProgress * 100 / 4);

        // Python (8 chapitres)
        int pythonProgress = progressManager.getProgress("Python");
        tvPythonProgress.setText(pythonProgress + " / 8 lessons");
        progressPython.setProgress(pythonProgress * 100 / 8);

        // C++ (8 chapitres)
        int cppProgress = progressManager.getProgress("C++");
        tvCppProgress.setText(cppProgress + " / 8 lessons");
        progressCpp.setProgress(cppProgress * 100 / 8);

        // Mettre à jour le compteur total
        int totalLessons = javaProgress + pythonProgress + cppProgress;
        tvLessonsCount.setText(String.valueOf(totalLessons));
    }

    private void setupClickListeners() {
        CardView cardJava = findViewById(R.id.cardJava);
        CardView cardPython = findViewById(R.id.cardPython);
        CardView cardCpp = findViewById(R.id.cardCpp);

        cardJava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ChaptersActivity.class);
                intent.putExtra("LANGUAGE_NAME", "Java");
                startActivity(intent);
            }
        });

        cardPython.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ChaptersActivity.class);
                intent.putExtra("LANGUAGE_NAME", "Python");
                startActivity(intent);
            }
        });

        cardCpp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ChaptersActivity.class);
                intent.putExtra("LANGUAGE_NAME", "C++");
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Mettre à jour l'affichage quand on revient à l'accueil
        updateProgressDisplay();
    }
}