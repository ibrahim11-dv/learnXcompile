package com.example.learnxcompile;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;

public class SubscriptionActivity extends AppCompatActivity {

    private EditText etCardNumber, etCardHolder, etExpiryDate, etCvv;
    private Button btnSubscribe;
    private String languageName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription);

        etCardNumber = findViewById(R.id.etCardNumber);
        etCardHolder = findViewById(R.id.etCardHolder);
        etExpiryDate = findViewById(R.id.etExpiryDate);
        etCvv = findViewById(R.id.etCvv);
        btnSubscribe = findViewById(R.id.btnSubscribe);

        languageName = getIntent().getStringExtra("LANGUAGE_NAME");

        btnSubscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processPayment();
            }
        });
    }

    private void processPayment() {
        // Vérifier que tous les champs sont remplis
        if (etCardNumber.getText().toString().isEmpty() ||
                etCardHolder.getText().toString().isEmpty() ||
                etExpiryDate.getText().toString().isEmpty() ||
                etCvv.getText().toString().isEmpty()) {
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            return;
        }

        // Vérification simple du numéro de carte (16 chiffres)
        if (etCardNumber.getText().toString().length() != 16) {
            Toast.makeText(this, "Numéro de carte invalide (16 chiffres)", Toast.LENGTH_SHORT).show();
            return;
        }

        // Vérification CVV (3 chiffres)
        if (etCvv.getText().toString().length() != 3) {
            Toast.makeText(this, "CVV invalide (3 chiffres)", Toast.LENGTH_SHORT).show();
            return;
        }

        // Simulation de paiement
        Toast.makeText(this, "Paiement de 5$ effectué avec succès !", Toast.LENGTH_LONG).show();

        // Sauvegarder que l'utilisateur a payé pour ce cours
        SharedPreferences prefs = getSharedPreferences("SubscriptionPrefs", MODE_PRIVATE);
        prefs.edit().putBoolean("subscribed_" + languageName, true).apply();

        // Rediriger vers la page des chapitres
        Intent intent = new Intent(SubscriptionActivity.this, ChaptersActivity.class);
        intent.putExtra("LANGUAGE_NAME", languageName);
        startActivity(intent);
        finish();
    }
}