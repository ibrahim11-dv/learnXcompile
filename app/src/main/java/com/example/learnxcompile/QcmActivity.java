package com.example.learnxcompile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.example.learnxcompile.Controllers.ChapterController;

public class QcmActivity extends AppCompatActivity {

    private ChapterController chapterController;
    private String chapterTitle;
    private int chapterId;
    private int selectedOption = -1;
    private boolean answered = false;

    private TextView tvQuestion, tvOptionA, tvOptionB, tvOptionC, tvOptionD;
    private CardView cardOptionA, cardOptionB, cardOptionC, cardOptionD;
    private View cardResult;
    private TextView tvResultExplanation;
    private CardView btnCheck, btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qcm);

        chapterController = new ChapterController(this);
        chapterTitle = getIntent().getStringExtra("CHAPTER_TITLE");
        chapterId = getIntent().getIntExtra("CHAPTER_ID", -1);

        initViews();
        setupQuiz();
        setupClickListeners();
    }

    private void initViews() {
        tvQuestion = findViewById(R.id.tvQuestion);
        tvOptionA = findViewById(R.id.tvOptionA);
        tvOptionB = findViewById(R.id.tvOptionB);
        tvOptionC = findViewById(R.id.tvOptionC);
        tvOptionD = findViewById(R.id.tvOptionD);

        cardOptionA = findViewById(R.id.optionA);
        cardOptionB = findViewById(R.id.optionB);
        cardOptionC = findViewById(R.id.optionC);
        cardOptionD = findViewById(R.id.optionD);

        cardResult = findViewById(R.id.cardResult);
        tvResultExplanation = findViewById(R.id.tvResultExplanation);
        btnCheck = findViewById(R.id.btnCheck);
        btnNext = findViewById(R.id.btnNext);

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());
    }

    private void setupQuiz() {
        tvQuestion.setText(chapterController.getQuestionForChapter(chapterId));
        String[] options = chapterController.getOptionsForChapter(chapterId);
        tvOptionA.setText(options[0]);
        tvOptionB.setText(options[1]);
        tvOptionC.setText(options[2]);
        tvOptionD.setText(options[3]);
    }

    private void setupClickListeners() {
        cardOptionA.setOnClickListener(v -> selectOption(0));
        cardOptionB.setOnClickListener(v -> selectOption(1));
        cardOptionC.setOnClickListener(v -> selectOption(2));
        cardOptionD.setOnClickListener(v -> selectOption(3));

        btnCheck.setOnClickListener(v -> checkAnswer());
        btnNext.setOnClickListener(v -> {
            Intent intent = new Intent(QcmActivity.this, TestActivity.class);
            intent.putExtra("CHAPTER_TITLE", chapterTitle);
            intent.putExtra("CHAPTER_ID", chapterId);
            startActivity(intent);
        });
    }

    private void selectOption(int index) {
        if (answered) return;
        selectedOption = index;
        resetOptionColors();
        CardView selected = getCardByIndex(index);
        selected.setCardBackgroundColor(0xFFE8EAFD);
    }

    private void resetOptionColors() {
        cardOptionA.setCardBackgroundColor(0xFFFFFFFF);
        cardOptionB.setCardBackgroundColor(0xFFFFFFFF);
        cardOptionC.setCardBackgroundColor(0xFFFFFFFF);
        cardOptionD.setCardBackgroundColor(0xFFFFFFFF);
    }

    private CardView getCardByIndex(int index) {
        if (index == 0) return cardOptionA;
        if (index == 1) return cardOptionB;
        if (index == 2) return cardOptionC;
        return cardOptionD;
    }

    private void checkAnswer() {
        if (selectedOption == -1) {
            Toast.makeText(this, "Please select an option", Toast.LENGTH_SHORT).show();
            return;
        }

        answered = true;
        int correctIndex = chapterController.getCorrectOptionIndex(chapterId);
        
        if (selectedOption == correctIndex) {
            getCardByIndex(selectedOption).setCardBackgroundColor(0xFFC8E6C9); // Green
        } else {
            getCardByIndex(selectedOption).setCardBackgroundColor(0xFFFFCDD2); // Red
            getCardByIndex(correctIndex).setCardBackgroundColor(0xFFC8E6C9); // Show correct
        }

        tvResultExplanation.setText(chapterController.getExplanationForChapter(chapterId));
        cardResult.setVisibility(View.VISIBLE);
        btnCheck.setVisibility(View.GONE);
        btnNext.setVisibility(View.VISIBLE);
    }
}
