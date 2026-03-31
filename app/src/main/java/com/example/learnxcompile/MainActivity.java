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

import com.example.learnxcompile.Controllers.authController;
import com.example.learnxcompile.Models.authModel;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText etEmail         = findViewById(R.id.etEmail);
        EditText etPassword      = findViewById(R.id.etPassword);
        Button btnSignIn         = findViewById(R.id.btnSignIn);
        TextView tvSignUp        = findViewById(R.id.tvSignUp);
        authController ac = new authController(this);
        authModel am = new authModel(this);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailOrUsername = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                if (ac.authenticate(emailOrUsername, password)) {
                    // Get real username from DB
                    String realUsername = am.getUsername(emailOrUsername);
                    
                    // Save username to SharedPreferences
                    SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("username", realUsername);
                    editor.apply();

                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(MainActivity.this, "Wrong credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tvSignUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intentSignUp = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intentSignUp);
            }
        });
    }
}