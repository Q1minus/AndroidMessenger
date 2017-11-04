package com.example.vadim.androidmesseger;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

public class AuthorizationActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText username, password;
    private Button buttonLogin, buttonRegistration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);

        username = findViewById(R.id.UsernameField);
        password = findViewById(R.id.PasswordField);

        buttonLogin = findViewById(R.id.LoginButton);
        buttonRegistration = findViewById(R.id.RegistrationButton);

        buttonLogin.setOnClickListener(this);
        buttonRegistration.setOnClickListener(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("username", username.getText().toString());
        outState.putString("password", password.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedState) {
        super.onRestoreInstanceState(savedState);

        username.setText(savedState.getString("username"));
        password.setText(savedState.getString("password"));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
        case R.id.LoginButton:
            // TODO Find user in DB, go to UserActivity
            break;
        case R.id.RegistrationButton:
            // TODO Go to RegistrationActivity
            break;
        }
    }
}
