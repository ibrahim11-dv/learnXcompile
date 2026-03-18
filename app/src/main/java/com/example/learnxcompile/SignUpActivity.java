package com.example.learnxcompile;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.learnxcompile.Controllers.authController;

public class SignUpActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        TextView tvSignInBack = findViewById(R.id.tvSignInBack);
        EditText etUsername = findViewById(R.id.etSignUpUsername);
        EditText etEmail = findViewById(R.id.etSignUpEmail);
        EditText etPassword = findViewById(R.id.etSignUpPassword);
        Button submit = findViewById(R.id.btnSignUpSubmit);
        authController ac = new authController(this);
        submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                if(!ac.userExists(username,email)){
                    ac.addUser(username, email, password);
                    Toast.makeText(SignUpActivity.this, "user signed up successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(SignUpActivity.this, "user already exists !!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tvSignInBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Goes back to MainActivity
            }
        });
    }
}
