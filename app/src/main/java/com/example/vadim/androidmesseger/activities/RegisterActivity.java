package com.example.vadim.androidmesseger.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vadim.androidmesseger.R;
import com.example.vadim.androidmesseger.database.UserDBHelper;


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    private static final int PASSWORD_MIN_LENGTH = 6;

    private EditText username, email, password;
    private Button buttonConfirm;
    private UserDBHelper userDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = findViewById(R.id.UsernameField);
        email = findViewById(R.id.EmailField);
        password = findViewById(R.id.PasswordField);

        buttonConfirm = findViewById(R.id.ConfirmButton);
        buttonConfirm.setOnClickListener(this);

        userDbHelper = new UserDBHelper(this);
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
        String stringUsername = username.getText().toString();
        String stringEmail = email.getText().toString();
        String stringPassword = password.getText().toString();

        switch (view.getId()) {
        case R.id.ConfirmButton:
            /* Check email and password */
            if (!Patterns.EMAIL_ADDRESS.matcher(stringEmail).matches()) {
                String toastMessage = "Bad username!";
                Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_LONG).show();
            }
            if (password.length() < PASSWORD_MIN_LENGTH) {
                String toastMessage = String.format("Password must be long that %d symbols", PASSWORD_MIN_LENGTH);
                Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_LONG).show();
            }

            /* Find username and email in database */
            if (userDbHelper.isExistsUsername(stringUsername)) {
                String toastMessage = String.format("User with username '%s' already exists.", username);
                Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_LONG).show();
            } else if (userDbHelper.isExistsUsername(stringEmail)) {
                String toastMessage = String.format("User with email '%s' already exists.", email);
                Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_LONG).show();
            } else {
                long newRowId = userDbHelper.addUser(stringUsername, stringEmail, stringPassword);

                if (newRowId == -1) {
                    Toast.makeText(getApplicationContext(),"Insert to database error!", Toast.LENGTH_SHORT).show();
                } else {
                    finish();
                }
            }
            break;
        }
    }

}
