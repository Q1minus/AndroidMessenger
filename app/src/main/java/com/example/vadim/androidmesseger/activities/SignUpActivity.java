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


public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{
    private static final int PASSWORD_MIN_LENGTH = 6;

    private EditText usernameEdit, emailEdit, passwordEdit;
    private Button confirmButton;
    private UserDBHelper userDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        usernameEdit = findViewById(R.id.RegistrationUsernameField);
        emailEdit = findViewById(R.id.RegistrationEmailField);
        passwordEdit = findViewById(R.id.RegistrationPasswordField);

        confirmButton = findViewById(R.id.ConfirmButton);
        confirmButton.setOnClickListener(this);

        userDbHelper = new UserDBHelper(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("username", usernameEdit.getText().toString());
        outState.putString("email", emailEdit.getText().toString());
        outState.putString("password", passwordEdit.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedState) {
        super.onRestoreInstanceState(savedState);

        usernameEdit.setText(savedState.getString("username"));
        emailEdit.setText(savedState.getString("email"));
        passwordEdit.setText(savedState.getString("password"));
    }

    @Override
    public void onClick(View view) {
        String stringUsername = usernameEdit.getText().toString();
        String stringEmail = emailEdit.getText().toString();
        String stringPassword = passwordEdit.getText().toString();

        switch (view.getId()) {
        case R.id.ConfirmButton:
            /* Check email and password */
            if (!Patterns.EMAIL_ADDRESS.matcher(stringEmail).matches()) {
                String toastMessage = "Bad username!";
                Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_LONG).show();
            }
            if (passwordEdit.length() < PASSWORD_MIN_LENGTH) {
                String toastMessage = String.format("Password must be long that %d symbols", PASSWORD_MIN_LENGTH);
                Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_LONG).show();
            }

            /* Find username and email in database */
            if (userDbHelper.isExistsUsername(stringUsername)) {
                String toastMessage = String.format("User with username '%s' already exists.", usernameEdit);
                Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_LONG).show();
            } else if (userDbHelper.isExistsUsername(stringEmail)) {
                String toastMessage = String.format("User with email '%s' already exists.", emailEdit);
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
