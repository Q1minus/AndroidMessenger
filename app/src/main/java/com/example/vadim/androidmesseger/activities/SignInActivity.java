package com.example.vadim.androidmesseger.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vadim.androidmesseger.R;
import com.example.vadim.androidmesseger.database.UserDBHelper;
import com.example.vadim.androidmesseger.models.UserModel;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText username, password;
    private Button buttonLogin, buttonRegistration;
    private UserDBHelper userDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        username = findViewById(R.id.AuthorizationUsernameField);
        password = findViewById(R.id.AuthorizationPasswordField);

        buttonLogin = findViewById(R.id.LoginButton);
        buttonRegistration = findViewById(R.id.RegistrationButton);

        buttonLogin.setOnClickListener(this);
        buttonRegistration.setOnClickListener(this);

        userDbHelper = new UserDBHelper(this);
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
        String stringUsername = username.getText().toString();
        String stringPassword = password.getText().toString();

        switch (view.getId()){
        case R.id.LoginButton:
            UserModel user =  userDbHelper.Authentication(stringUsername, stringPassword);

            if (user != null) {
                startUserMessagesActivity(user);
            } else {
                Toast.makeText(getApplicationContext(), R.string.BadUsernameOrPassword, Toast.LENGTH_LONG).show();
            }
            break;
        case R.id.RegistrationButton:
            startRegisterActivity();
            break;
        }
    }

    private void startUserMessagesActivity(UserModel user) {
        Intent intent = new Intent(this, UserMessagesActivity.class);
        user.putExtraUser(intent);
        startActivity(intent);
    }

    private void startRegisterActivity() {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }
}
