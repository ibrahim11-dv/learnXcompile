package com.example.learnxcompile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import android.view.LayoutInflater;

import com.example.learnxcompile.Controllers.ChapterController;
import com.example.learnxcompile.Items.Language;

public class TestActivity extends AppCompatActivity {

    private ChapterController chapterController;
    private int chapterId;
    private String languageName;
    private TextView tvInstructions, tvHintText, tvExpectedOutput;
    private EditText etCodeInput;
    private View btnSubmit, btnBack, btnHint, btnReset;
    private CardView cardHint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        chapterController = new ChapterController(this);
        chapterId = getIntent().getIntExtra("CHAPTER_ID", -1);
        languageName = getIntent().getStringExtra("LANGUAGE_NAME");

        tvInstructions = findViewById(R.id.tvInstructions);
        tvHintText = findViewById(R.id.tvHintText);
        tvExpectedOutput = findViewById(R.id.tvExpectedOutput);
        etCodeInput = findViewById(R.id.etCodeInput);
        btnSubmit = findViewById(R.id.btnRunCode);
        btnBack = findViewById(R.id.btnBack);
        btnHint = findViewById(R.id.btnHint);
        btnReset = findViewById(R.id.btnReset);
        cardHint = findViewById(R.id.cardHint);

        loadTestData();

        btnSubmit.setOnClickListener(v -> checkResult());
        btnBack.setOnClickListener(v -> finish());
        
        btnHint.setOnClickListener(v -> {
            if (cardHint.getVisibility() == View.GONE) {
                cardHint.setVisibility(View.VISIBLE);
            } else {
                cardHint.setVisibility(View.GONE);
            }
        });

        btnReset.setOnClickListener(v -> {
            etCodeInput.setText(chapterController.getStarterCode(chapterId));
        });
    }

    private void loadTestData() {
        tvInstructions.setText(chapterController.getTestInstructions(chapterId));
        etCodeInput.setText(chapterController.getStarterCode(chapterId));
        tvHintText.setText(chapterController.getHint(chapterId));
        tvExpectedOutput.setText(chapterController.getExpectedOutput(chapterId));
    }

    private void checkResult() {
        String input = etCodeInput.getText().toString().trim();
        String expected = chapterController.getExpectedOutput(chapterId).trim();

        if (input.contains(expected)) {
            Toast.makeText(this, "Correct !", Toast.LENGTH_SHORT).show();
            chapterController.completeChapter(chapterId);
            
            checkIfShouldShowSubscription();
        } else {
            Toast.makeText(this, "Réessayez...", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkIfShouldShowSubscription() {
        Language language = chapterController.getLanguageByName(languageName);
        int currentOrder = -1;
        if (language != null && language.chapters != null) {
            for (Language.Chapter c : language.chapters) {
                if (c.id == chapterId) {
                    currentOrder = c.order;
                    break;
                }
            }
        }

        SharedPreferences prefs = getSharedPreferences("SubscriptionPrefs", MODE_PRIVATE);
        boolean isSubscribed = prefs.getBoolean("all_courses_subscribed", false) || 
                              prefs.getBoolean("subscribed_" + languageName, false);

        if (currentOrder == 4 && !isSubscribed) {
            showSubscriptionPopUp();
        } else {
            goToChapters();
        }
    }

    private void goToChapters() {
        Intent intent = new Intent(this, ChaptersActivity.class);
        intent.putExtra("LANGUAGE_NAME", languageName);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }

    private void showSubscriptionPopUp() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_subscription_options, null);
        builder.setView(view);

        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }

        TextView tvTitle = view.findViewById(R.id.tvCourseTitle);
        tvTitle.setText("Cours " + languageName + " uniquement");

        view.findViewById(R.id.cardSingleCourse).setOnClickListener(v -> {
            Intent intent = new Intent(this, SubscriptionActivity.class);
            intent.putExtra("LANGUAGE_NAME", languageName);
            intent.putExtra("PLAN_TYPE", "SINGLE");
            startActivity(intent);
            dialog.dismiss();
            finish();
        });

        view.findViewById(R.id.cardAllCourses).setOnClickListener(v -> {
            Intent intent = new Intent(this, SubscriptionActivity.class);
            intent.putExtra("LANGUAGE_NAME", "ALL");
            intent.putExtra("PLAN_TYPE", "ALL_COURSES");
            startActivity(intent);
            dialog.dismiss();
            finish();
        });

        view.findViewById(R.id.btnCancel).setOnClickListener(v -> {
            dialog.dismiss();
            goToChapters();
        });

        dialog.show();
    }
}
