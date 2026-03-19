package com.example.learnxcompile;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.github.barteksc.pdfviewer.PDFView;

public class PdfViewerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            setContentView(R.layout.activity_pdf_viewer);

            String pdfName = getIntent().getStringExtra("PDF_NAME");
            int startPage = getIntent().getIntExtra("START_PAGE", 0);
            int endPage = getIntent().getIntExtra("END_PAGE", 0);
            final String languageName = getIntent().getStringExtra("LANGUAGE_NAME");
            final int chapterIndex = getIntent().getIntExtra("CHAPTER_INDEX", -1);
            final int currentProgress = getIntent().getIntExtra("CURRENT_PROGRESS", 0);

            if (pdfName == null) {
                Toast.makeText(this, "Erreur: Nom du PDF manquant!", Toast.LENGTH_LONG).show();
                finish();
                return;
            }

            PDFView pdfView = findViewById(R.id.pdfView);

            if (pdfView == null) {
                Toast.makeText(this, "Erreur: Vue PDF introuvable!", Toast.LENGTH_LONG).show();
                finish();
                return;
            }

            // Créer le tableau des pages à afficher
            int[] pages = new int[endPage - startPage + 1];
            for (int i = 0; i < pages.length; i++) {
                pages[i] = startPage + i;
            }

            try {
                pdfView.fromAsset(pdfName)
                        .pages(pages)
                        .defaultPage(0)
                        .enableSwipe(true)
                        .enableDoubletap(true)
                        .load();
            } catch (Exception e) {
                Toast.makeText(this, "Erreur: " + e.getMessage(), Toast.LENGTH_LONG).show();
                finish();
            }

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Erreur critique: " + e.getMessage(), Toast.LENGTH_LONG).show();
            finish();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        String languageName = getIntent().getStringExtra("LANGUAGE_NAME");
        int chapterIndex = getIntent().getIntExtra("CHAPTER_INDEX", -1);
        int currentProgress = getIntent().getIntExtra("CURRENT_PROGRESS", 0);

        // Si l'utilisateur a consulté le chapitre en cours
        if (chapterIndex == currentProgress && languageName != null) {
            ProgressManager progressManager = new ProgressManager(this);
            progressManager.setProgress(languageName, currentProgress + 1);

            Toast.makeText(this,
                    "Chapitre terminé ! Progression: " + (currentProgress + 1) + "/" +
                            progressManager.getTotalChapters(languageName),
                    Toast.LENGTH_SHORT).show();
        }
    }
}