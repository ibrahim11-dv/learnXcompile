package com.example.learnxcompile;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;

public class SubscriptionActivity extends AppCompatActivity {

    private EditText etCardNumber, etCardHolder, etExpiryDate, etCvv;
    private Button btnSubscribe;
    private TextView tvSubscriptionTitle, tvSubscriptionPrice;
    private String languageName;
    private String planType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription);

        // Initialize views
        etCardNumber = findViewById(R.id.etCardNumber);
        etCardHolder = findViewById(R.id.etCardHolder);
        etExpiryDate = findViewById(R.id.etExpiryDate);
        etCvv = findViewById(R.id.etCvv);
        btnSubscribe = findViewById(R.id.btnSubscribe);
        tvSubscriptionTitle = findViewById(R.id.tvSubscriptionTitle);
        tvSubscriptionPrice = findViewById(R.id.tvSubscriptionPrice);

        // Get data from intent
        languageName = getIntent().getStringExtra("LANGUAGE_NAME");
        planType = getIntent().getStringExtra("PLAN_TYPE");

        // Update UI based on plan type
        updateUI();

        btnSubscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processPayment();
            }
        });
    }

    private void updateUI() {
        if ("ALL_COURSES".equals(planType)) {
            tvSubscriptionTitle.setText("Pack Premium");
            tvSubscriptionPrice.setText("15$ pour tous les cours");
            btnSubscribe.setText("Payer 15$");
        } else {
            tvSubscriptionTitle.setText("Cours " + languageName);
            tvSubscriptionPrice.setText("5$ pour ce cours");
            btnSubscribe.setText("Payer 5$");
        }
    }

    private void processPayment() {
        // Simple validation
        if (etCardNumber.getText().toString().isEmpty() ||
                etCardHolder.getText().toString().isEmpty() ||
                etExpiryDate.getText().toString().isEmpty() ||
                etCvv.getText().toString().isEmpty()) {
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            return;
        }

        if (etCardNumber.getText().toString().length() != 16) {
            Toast.makeText(this, "Numéro de carte invalide (16 chiffres requis)", Toast.LENGTH_SHORT).show();
            return;
        }

        // Modal for success
        String successMessage = "ALL_COURSES".equals(planType) ? 
                "Félicitations ! Vous avez débloqué l'accès à TOUS nos cours." : 
                "Félicitations ! Vous avez débloqué le cours de " + languageName + ".";

        new AlertDialog.Builder(this)
                .setTitle("Paiement réussi")
                .setMessage(successMessage)
                .setPositiveButton("Commencer", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        saveSubscription();
                        redirectToContent();
                    }
                })
                .setCancelable(false)
                .show();
    }

    private void saveSubscription() {
        SharedPreferences prefs = getSharedPreferences("SubscriptionPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        
        if ("ALL_COURSES".equals(planType)) {
            editor.putBoolean("all_courses_subscribed", true);
        } else {
            editor.putBoolean("subscribed_" + languageName, true);
        }
        editor.apply();
    }

    private void redirectToContent() {
        Intent intent;
        if ("ALL_COURSES".equals(planType)) {
            // Return to home where everything is now unlocked
            intent = new Intent(SubscriptionActivity.this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        } else {
            // Go directly to the chapters of the language they just bought
            intent = new Intent(SubscriptionActivity.this, ChaptersActivity.class);
            intent.putExtra("LANGUAGE_NAME", languageName);
        }
        startActivity(intent);
        finish();
    }
}