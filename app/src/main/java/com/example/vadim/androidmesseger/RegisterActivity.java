package com.example.vadim.androidmesseger;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText username, email, password;
    private Button buttonConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = findViewById(R.id.UsernameField);
        email = findViewById(R.id.EmailField);
        password = findViewById(R.id.PasswordField);

        buttonConfirm = findViewById(R.id.ConfirmButton);
        buttonConfirm.setOnClickListener(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("username", username.getText().toString());
        outState.putString("email", email.getText().toString());
        outState.putString("password", password.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedState) {
        super.onRestoreInstanceState(savedState);

        username.setText(savedState.getString("username"));
        email.setText(savedState.getString("email"));
        password.setText(savedState.getString("password"));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ConfirmButton:
                // TODO confirm rules. Added in DB
                break;
        }
    }
}
