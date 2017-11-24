package com.example.vadim.androidmesseger.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.vadim.androidmesseger.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class SignInActivity extends AppCompatActivity implements View.OnClickListener {
    EditText emailEdit, passwordEdit;
    Button loginButton, registrationButton;
    ProgressBar progressBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        progressBar = findViewById(R.id.progressBar);
        emailEdit = findViewById(R.id.AuthorizationUsernameField);
        passwordEdit = findViewById(R.id.AuthorizationPasswordField);
        loginButton = findViewById(R.id.LoginButton);
        registrationButton = findViewById(R.id.RegistrationButton);

        loginButton.setOnClickListener(this);
        registrationButton.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("username", emailEdit.getText().toString());
        outState.putString("password", passwordEdit.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedState) {
        super.onRestoreInstanceState(savedState);

        emailEdit.setText(savedState.getString("username"));
        passwordEdit.setText(savedState.getString("password"));
    }

    @Override
    public void onClick(View view) {
        String username = emailEdit.getText().toString();
        String password = passwordEdit.getText().toString();

        switch (view.getId()){
        case R.id.LoginButton:
            signIn(username, password);
            break;
        case R.id.RegistrationButton:
            Intent intent = new Intent(this, SignUpActivity.class);
            startActivity(intent);
            break;
        }
    }

    private void signIn(String email, String password) {
        if (!validateForm()) {
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(SignInActivity.this, UserMessagesActivity.class);
                            SignInActivity.this.startActivity(intent);
                        } else {
                            Toast.makeText(SignInActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }

                        progressBar.setVisibility(View.GONE);
                    }
                });
    }
    
    private boolean validateForm() {
        boolean valid = true;
        String email = emailEdit.getText().toString();
        String password = passwordEdit.getText().toString();

        if (TextUtils.isEmpty(email)) {
            emailEdit.setError("Email required!");
            valid = false;
        } else {
            emailEdit.setError(null);
        }

        if (TextUtils.isEmpty(password)) {
            passwordEdit.setError("Password required!");
            valid = false;
        } else {
            passwordEdit.setError(null);
        }

        return valid;
    }

}

