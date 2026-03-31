package com.example.learnxcompile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.learnxcompile.Controllers.ChapterController;

public class ChapterExplanationActivity extends AppCompatActivity {

    private ChapterController chapterController;
    private String chapterTitle;
    private int chapterId;
    private String languageName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_explanation);

        chapterController = new ChapterController(this);
        chapterTitle = getIntent().getStringExtra("CHAPTER_TITLE");
        chapterId = getIntent().getIntExtra("CHAPTER_ID", -1);
        languageName = getIntent().getStringExtra("LANGUAGE_NAME");

        initViews();
    }

    private void initViews() {
        TextView tvStepTitle = findViewById(R.id.tvStepTitle);
        tvStepTitle.setText(chapterTitle);

        TextView tvExplanationText = findViewById(R.id.tvExplanationText);
        tvExplanationText.setText(chapterController.getExplanationText(chapterId));

        // Handle Key Points
        LinearLayout layoutKeyPoints = findViewById(R.id.layoutKeyPoints);
        String keyPointsRaw = chapterController.getKeyPoints(chapterId);
        if (keyPointsRaw != null && !keyPointsRaw.isEmpty()) {
            String[] points = keyPointsRaw.split("\\|");
            for (String point : points) {
                TextView tvPoint = new TextView(this);
                tvPoint.setText("• " + point.trim());
                tvPoint.setTextSize(14);
                tvPoint.setTextColor(0xFF3A3D50);
                tvPoint.setPadding(0, 8, 0, 8);
                layoutKeyPoints.addView(tvPoint);
            }
        }

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        findViewById(R.id.btnNext).setOnClickListener(v -> {
            Intent intent = new Intent(ChapterExplanationActivity.this, CodeExampleActivity.class);
            intent.putExtra("CHAPTER_TITLE", chapterTitle);
            intent.putExtra("CHAPTER_ID", chapterId);
            intent.putExtra("LANGUAGE_NAME", languageName);
            startActivity(intent);
        });
    }
}
