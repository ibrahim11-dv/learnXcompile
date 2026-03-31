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
import android.view.LayoutInflater;

import com.example.learnxcompile.Controllers.ChapterController;
import com.example.learnxcompile.Items.Language;

public class TestActivity extends AppCompatActivity {

    private ChapterController chapterController;
    private int chapterId;
    private String languageName;
    private TextView tvInstructions;
    private EditText etCodeInput;
    private View btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        chapterController = new ChapterController(this);
        chapterId = getIntent().getIntExtra("CHAPTER_ID", -1);
        languageName = getIntent().getStringExtra("LANGUAGE_NAME");

        tvInstructions = findViewById(R.id.tvInstructions);
        etCodeInput = findViewById(R.id.etCodeInput);
        btnSubmit = findViewById(R.id.btnRunCode);

        loadTestData();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkResult();
            }
        });
    }

    private void loadTestData() {
        tvInstructions.setText(chapterController.getTestInstructions(chapterId));
        etCodeInput.setText(chapterController.getStarterCode(chapterId));
    }

    private void checkResult() {
        String input = etCodeInput.getText().toString().trim();
        String expected = chapterController.getExpectedOutput(chapterId).trim();

        if (input.contains(expected)) {
            Toast.makeText(this, "Correct !", Toast.LENGTH_SHORT).show();
            chapterController.completeChapter(chapterId);
            
            // Check if this was the 4th chapter
            checkIfShouldShowSubscription();
        } else {
            Toast.makeText(this, "Réessayez...", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkIfShouldShowSubscription() {
        Language language = chapterController.getLanguageByName(languageName);
        int currentOrder = -1;
        for (Language.Chapter c : language.chapters) {
            if (c.id == chapterId) {
                currentOrder = c.order;
                break;
            }
        }

        SharedPreferences prefs = getSharedPreferences("SubscriptionPrefs", MODE_PRIVATE);
        boolean isSubscribed = prefs.getBoolean("all_courses_subscribed", false) || 
                              prefs.getBoolean("subscribed_" + languageName, false);

        if (currentOrder == 4 && !isSubscribed) {
            showSubscriptionPopUp();
        } else {
            finish();
        }
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
            finish();
        });

        dialog.show();
    }
}
