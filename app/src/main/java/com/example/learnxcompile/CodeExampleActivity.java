package com.example.learnxcompile;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.learnxcompile.Controllers.ChapterController;
import java.util.Map;

public class CodeExampleActivity extends AppCompatActivity {

    private ChapterController chapterController;
    private String chapterTitle;
    private int chapterId;
    private String languageName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_example);

        chapterController = new ChapterController(this);
        chapterTitle = getIntent().getStringExtra("CHAPTER_TITLE");
        chapterId = getIntent().getIntExtra("CHAPTER_ID", -1);
        languageName = getIntent().getStringExtra("LANGUAGE_NAME");

        initViews();
    }

    private void initViews() {
        TextView tvStepTitle = findViewById(R.id.tvStepTitle);
        tvStepTitle.setText(chapterTitle);

        Map<String, String> content = chapterController.getCodeContent(chapterId);
        
        TextView tvContextText = findViewById(R.id.tvContextText);
        TextView tvLanguageTag = findViewById(R.id.tvLanguageTag);
        TextView tvCodeSnippet = findViewById(R.id.tvCodeSnippet);
        TextView tvExpectedOutput = findViewById(R.id.tvExpectedOutput);

        tvContextText.setText(content.getOrDefault("context", "No description available."));
        tvLanguageTag.setText(content.getOrDefault("language", "CODE"));
        tvCodeSnippet.setText(content.getOrDefault("code", ""));
        tvExpectedOutput.setText(content.getOrDefault("output", ""));

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        findViewById(R.id.btnNext).setOnClickListener(v -> {
            Intent intent = new Intent(CodeExampleActivity.this, QcmActivity.class);
            intent.putExtra("CHAPTER_TITLE", chapterTitle);
            intent.putExtra("CHAPTER_ID", chapterId);
            intent.putExtra("LANGUAGE_NAME", languageName);
            startActivity(intent);
        });
    }
}
