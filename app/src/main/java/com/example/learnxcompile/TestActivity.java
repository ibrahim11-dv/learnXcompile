package com.example.learnxcompile;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.example.learnxcompile.Controllers.ChapterController;

public class TestActivity extends AppCompatActivity {

    private ChapterController chapterController;
    private String chapterTitle;
    private int chapterId;

    private TextView tvInstructions, tvExpectedOutput, tvLanguageTag;
    private EditText etCodeInput;
    private CardView btnRunCode, btnNext, cardResult;
    private TextView tvResultLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        chapterController = new ChapterController(this);
        chapterTitle = getIntent().getStringExtra("CHAPTER_TITLE");
        chapterId = getIntent().getIntExtra("CHAPTER_ID", -1);

        initViews();
        setupChallenge();
    }

    private void initViews() {
        tvInstructions = findViewById(R.id.tvInstructions);
        tvExpectedOutput = findViewById(R.id.tvExpectedOutput);
        tvLanguageTag = findViewById(R.id.tvLanguageTag);
        etCodeInput = findViewById(R.id.etCodeInput);
        btnRunCode = findViewById(R.id.btnRunCode);
        btnNext = findViewById(R.id.btnNext);
        cardResult = findViewById(R.id.cardResult);
        tvResultLabel = findViewById(R.id.tvResultLabel);

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());
        
        findViewById(R.id.btnReset).setOnClickListener(v -> 
            etCodeInput.setText(chapterController.getStarterCode(chapterId))
        );

        btnRunCode.setOnClickListener(v -> runCode());
        
        btnNext.setOnClickListener(v -> {
            chapterController.completeChapter(chapterId);
            Toast.makeText(this, "Chapter Completed!", Toast.LENGTH_LONG).show();
            finishAffinity();
            startActivity(new android.content.Intent(this, HomeActivity.class));
        });
    }

    private void setupChallenge() {
        tvInstructions.setText(chapterController.getTestInstructions(chapterId));
        tvExpectedOutput.setText(chapterController.getExpectedOutput(chapterId));
        etCodeInput.setText(chapterController.getStarterCode(chapterId));
    }

    private void runCode() {
        // Simple logic: if code contains the expected print statements, it passes
        String input = etCodeInput.getText().toString();
        String expected = chapterController.getExpectedOutput(chapterId);
        
        // This is a very basic simulation of "running" code
        boolean passed = true;
        for (String line : expected.split("\n")) {
            if (!input.contains(line)) {
                passed = false;
                break;
            }
        }

        cardResult.setVisibility(View.VISIBLE);
        if (passed) {
            tvResultLabel.setText("  All tests passed!");
            tvResultLabel.setTextColor(0xFF4CAF50);
            btnNext.setVisibility(View.VISIBLE);
            btnRunCode.setVisibility(View.GONE);
        } else {
            tvResultLabel.setText("  Test failed. Check your output.");
            tvResultLabel.setTextColor(0xFFF44336);
        }
    }
}
