package com.example.vadim.androidmesseger.activities;

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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{
    EditText emailEdit, passwordEdit;
    Button confirmButton;
    ProgressBar progressBar;

    private FirebaseAuth mAuth;
    private DatabaseReference myRef;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        progressBar = findViewById(R.id.progressBar);
        emailEdit = findViewById(R.id.RegistrationEmailField);
        passwordEdit = findViewById(R.id.RegistrationPasswordField);
        confirmButton = findViewById(R.id.sign_up_button);

        confirmButton.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("email", emailEdit.getText().toString());
        outState.putString("password", passwordEdit.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedState) {
        super.onRestoreInstanceState(savedState);

        emailEdit.setText(savedState.getString("email"));
        passwordEdit.setText(savedState.getString("password"));
    }

    @Override
    public void onClick(View view) {
        String email = emailEdit.getText().toString().trim();
        String password = passwordEdit.getText().toString().trim();

        switch (view.getId()) {
        case R.id.sign_up_button:
            signUp(email, password);
            break;
        }
    }

    private void signUp(String email, String password) {
        if (!validateForm()) {
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(SignUpActivity.this, R.string.sign_up_success, Toast.LENGTH_LONG).show();

                            FirebaseUser user =  mAuth.getCurrentUser();
                            if (user != null) {
                                myRef.child("Users").child(user.getUid()).child("id").setValue(user.getUid());
                                myRef.child("Users").child(user.getUid()).child("email").setValue(user.getEmail());
                            }
                            finish();
                        } else {
                            Toast.makeText(SignUpActivity.this, R.string.sign_up_failed, Toast.LENGTH_LONG).show();
                        }

                        progressBar.setVisibility(View.GONE);
                    }
                });
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = emailEdit.getText().toString();
        if (TextUtils.isEmpty(email)) {
            emailEdit.setError("Required.");
            valid = false;
        } else {
            emailEdit.setError(null);
        }

        String password = passwordEdit.getText().toString();
        if (TextUtils.isEmpty(password)) {
            passwordEdit.setError("Required.");
            valid = false;
        } else {
            passwordEdit.setError(null);
        }

        return valid;
    }
}
