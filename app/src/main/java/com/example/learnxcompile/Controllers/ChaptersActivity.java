package com.example.learnxcompile.Controllers;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.example.learnxcompile.PdfViewerActivity;
import com.example.learnxcompile.ProgressManager;
import com.example.learnxcompile.R;

public class ChaptersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapters);

        // Récupérer le nom du langage cliqué
        String languageName = getIntent().getStringExtra("LANGUAGE_NAME");
        TextView tvLanguageTitle = findViewById(R.id.tvLanguageTitle);
        tvLanguageTitle.setText(languageName + " Chapters");

        // Gestionnaire de progression
        ProgressManager progressManager = new ProgressManager(this);
        int currentProgress = progressManager.getProgress(languageName);

        // Liste des chapitres selon le langage
        String[] chapters = null;
        int[] chapterStartPages = null;
        int[] chapterEndPages = null;

        if (languageName.equals("Java")) {
            chapters = new String[]{
                    "Chapitre 1: Notions élémentaires",
                    "Chapitre 2: Les méthodes",
                    "Chapitre 3: Les types non primitifs",
                    "Chapitre 4: Glossaire"
            };

            chapterStartPages = new int[]{0, 8, 11, 18};
            chapterEndPages = new int[]{7, 10, 17, 20};

        } else if (languageName.equals("Python")) {
            chapters = new String[]{
                    "Chapter 1: Python Basics",
                    "Chapter 2: Data Structures",
                    "Chapter 3: Functions",
                    "Chapter 4: File Handling",
                    "Chapter 5: OOP in Python",
                    "Chapter 6: Modules and Packages",
                    "Chapter 7: Error Handling",
                    "Chapter 8: Working with APIs"
            };

            chapterStartPages = new int[]{0, 10, 20, 30, 40, 50, 60, 70};
            chapterEndPages = new int[]{9, 19, 29, 39, 49, 59, 69, 79};

        } else if (languageName.equals("C++")) {
            chapters = new String[]{
                    "Chapter 1: C++ Fundamentals",
                    "Chapter 2: Pointers and References",
                    "Chapter 3: Memory Management",
                    "Chapter 4: STL (Standard Template Library)",
                    "Chapter 5: Classes and Objects",
                    "Chapter 6: Inheritance and Polymorphism",
                    "Chapter 7: File Handling",
                    "Chapter 8: Templates"
            };

            chapterStartPages = new int[]{0, 10, 20, 30, 40, 50, 60, 70};
            chapterEndPages = new int[]{9, 19, 29, 39, 49, 59, 69, 79};

        } else {
            chapters = new String[]{"No chapters available"};
            chapterStartPages = new int[]{0};
            chapterEndPages = new int[]{0};
        }

        // Setup RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerViewChapters);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        if (chapters != null && chapterStartPages != null && chapterEndPages != null) {
            final String[] finalChapters = chapters;
            final int[] finalStartPages = chapterStartPages;
            final int[] finalEndPages = chapterEndPages;
            final int finalCurrentProgress = currentProgress;
            final String finalLanguageName = languageName;

            recyclerView.setAdapter(new RecyclerView.Adapter<RecyclerView.ViewHolder>() {
                @Override
                public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                    View view = LayoutInflater.from(ChaptersActivity.this)
                            .inflate(R.layout.item_chapter, parent, false);
                    return new RecyclerView.ViewHolder(view) {};
                }

                @Override
                public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                    View itemView = holder.itemView;

                    TextView tvChapterNumber = itemView.findViewById(R.id.tvChapterNumber);
                    TextView tvChapterTitle = itemView.findViewById(R.id.tvChapterTitle);
                    TextView tvChapterDescription = itemView.findViewById(R.id.tvChapterDescription);
                    ImageView ivLock = itemView.findViewById(R.id.ivLock);

                    tvChapterNumber.setText(String.valueOf(position + 1));
                    tvChapterTitle.setText(finalChapters[position]);
                    tvChapterDescription.setText("Chapitre " + (position + 1));

                    // Gestion du verrouillage selon la progression
                    if (position <= finalCurrentProgress) {
                        // Chapitre débloqué
                        ivLock.setImageResource(R.drawable.ic_unlocked);
                        ivLock.setColorFilter(android.graphics.Color.parseColor("#5B6BF8"));

                        // Rendre cliquable
                        itemView.setOnClickListener(v -> {
                            Intent intent = new Intent(ChaptersActivity.this, PdfViewerActivity.class);
                            intent.putExtra("PDF_NAME", "poly2013.pdf");
                            intent.putExtra("START_PAGE", finalStartPages[position]);
                            intent.putExtra("END_PAGE", finalEndPages[position]);
                            intent.putExtra("LANGUAGE_NAME", finalLanguageName);
                            intent.putExtra("CHAPTER_INDEX", position);
                            intent.putExtra("CURRENT_PROGRESS", finalCurrentProgress);
                            startActivity(intent);
                        });
                    } else {
                        // Chapitre verrouillé
                        ivLock.setImageResource(R.drawable.ic_locked);
                        ivLock.setColorFilter(android.graphics.Color.parseColor("#A9A9A9"));

                        itemView.setOnClickListener(v -> {
                            Toast.makeText(ChaptersActivity.this,
                                    "Terminez d'abord le chapitre " + (finalCurrentProgress + 1),
                                    Toast.LENGTH_SHORT).show();
                        });
                    }
                }

                @Override
                public int getItemCount() {
                    return finalChapters.length;
                }
            });
        }
    }
}