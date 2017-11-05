package com.example.vadim.androidmesseger;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.vadim.androidmesseger.database.UserTableDBHelper;


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText username, email, password;
    private Button buttonConfirm;
    private UserTableDBHelper userTableDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = findViewById(R.id.UsernameField);
        email = findViewById(R.id.EmailField);
        password = findViewById(R.id.PasswordField);

        buttonConfirm = findViewById(R.id.ConfirmButton);
        buttonConfirm.setOnClickListener(this);

        userTableDbHelper = new UserTableDBHelper(this);
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
        SQLiteDatabase db = userTableDbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();

        switch (view.getId()) {
            case R.id.ConfirmButton:
                // TODO is valid Email username password ? Save : Error msg
                break;
        }
    }
}
