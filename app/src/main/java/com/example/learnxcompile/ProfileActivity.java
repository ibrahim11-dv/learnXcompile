package com.example.learnxcompile;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.learnxcompile.Controllers.ProfileController;
import com.example.learnxcompile.Controllers.ChapterController;
import com.example.learnxcompile.Items.Language;
import java.util.List;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    private ProfileController profileController;
    private ChapterController chapterController;
    private TextView tvProfileName, tvProfileEmail, tvProfileAvatar;
    private TextView tvJavaPercent, tvPythonPercent, tvCppPercent;
    private ProgressBar pbJava, pbPython, pbCpp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profileController = new ProfileController(this);
        chapterController = new ChapterController(this);
        
        initViews();
        loadUserProfile();
        loadProgress();
        setupNavigation();
    }

    private void initViews() {
        tvProfileName = findViewById(R.id.tvProfileName);
        tvProfileEmail = findViewById(R.id.tvProfileEmail);
        tvProfileAvatar = findViewById(R.id.tvProfileAvatar);
        
        tvJavaPercent = findViewById(R.id.tvJavaPercent);
        tvPythonPercent = findViewById(R.id.tvPythonPercent);
        tvCppPercent = findViewById(R.id.tvCppPercent);
        
        pbJava = findViewById(R.id.pbJava);
        pbPython = findViewById(R.id.pbPython);
        pbCpp = findViewById(R.id.pbCpp);

        findViewById(R.id.btnEditUsername).setOnClickListener(v -> showEditUsernameDialog());

        findViewById(R.id.btnLogout).setOnClickListener(v -> {
            SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
            prefs.edit().clear().apply();
            Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
    }

    private void loadUserProfile() {
        SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String username = prefs.getString("username", "");
        
        Map<String, String> details = profileController.getUserProfile(username);
        if (details != null && !details.isEmpty()) {
            String realName = details.get("username");
            tvProfileName.setText(realName);
            tvProfileEmail.setText(details.get("email"));
            
            if (realName != null && realName.length() >= 2) {
                tvProfileAvatar.setText(realName.substring(0, 2).toUpperCase());
            } else if (realName != null) {
                tvProfileAvatar.setText(realName.toUpperCase());
            }
        }
    }

    private void loadProgress() {
        List<Language> languages = chapterController.getLanguages();
        for (Language lang : languages) {
            if (lang.name.equalsIgnoreCase("Java")) {
                tvJavaPercent.setText(lang.progress + "%");
                pbJava.setProgress(lang.progress);
            } else if (lang.name.equalsIgnoreCase("Python")) {
                tvPythonPercent.setText(lang.progress + "%");
                pbPython.setProgress(lang.progress);
            } else if (lang.name.equalsIgnoreCase("C++")) {
                tvCppPercent.setText(lang.progress + "%");
                pbCpp.setProgress(lang.progress);
            }
        }
    }

    private void showEditUsernameDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Change Username");

        View viewInflated = LayoutInflater.from(this).inflate(R.layout.dialog_edit_username, null);
        final EditText input = viewInflated.findViewById(R.id.etNewUsername);
        builder.setView(viewInflated);

        builder.setPositiveButton("Save", (dialog, which) -> {
            String newUsername = input.getText().toString().trim();
            if (!newUsername.isEmpty()) {
                SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                String oldUsername = prefs.getString("username", "");
                
                if (profileController.updateUsername(oldUsername, newUsername)) {
                    prefs.edit().putString("username", newUsername).apply();
                    loadUserProfile();
                    Toast.makeText(ProfileActivity.this, "Username updated!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ProfileActivity.this, "Update failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    private void setupNavigation() {
        findViewById(R.id.navHome).setOnClickListener(v -> {
            startActivity(new Intent(this, HomeActivity.class));
            overridePendingTransition(0, 0);
            finish();
        });
    }
}
