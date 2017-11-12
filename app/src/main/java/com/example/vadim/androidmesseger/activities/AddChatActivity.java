package com.example.vadim.androidmesseger.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vadim.androidmesseger.R;
import com.example.vadim.androidmesseger.database.FriendListDBHelper;
import com.example.vadim.androidmesseger.database.UserDBHelper;
import com.example.vadim.androidmesseger.models.UserModel;

public class AddChatActivity extends AppCompatActivity implements View.OnClickListener{
    Button buttonAdd;
    EditText username, email;
    FriendListDBHelper friendListDBHelper;
    UserDBHelper userDBHelper;
    UserModel user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_chat);

        buttonAdd = findViewById(R.id.AddButton);
        buttonAdd.setOnClickListener(this);

        username = findViewById(R.id.AddChatUsernameField);
        email = findViewById(R.id.AddChatEmailField);

        friendListDBHelper = new FriendListDBHelper(this);
        userDBHelper = new UserDBHelper(this);

        user = new UserModel(getIntent());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("username", username.getText().toString());
        outState.putString("password", email.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedState) {
        super.onRestoreInstanceState(savedState);

        username.setText(savedState.getString("username"));
        email.setText(savedState.getString("password"));
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        case R.id.AddButton:
            String stringUsername = username.getText().toString();
            String stringEmail = email.getText().toString();
            if (stringUsername.isEmpty()) {
                stringUsername = "null";
            }
            if (stringEmail.isEmpty()) {
                stringEmail = "null";
            }

            UserModel foundUser = userDBHelper.findUser(stringUsername, stringEmail);
            if (foundUser != null) {
                long a = friendListDBHelper.addFriend(user.getId(), foundUser.getId());
                finish();
            }
            else {
                Toast.makeText(getApplicationContext(), R.string.UserNotFound, Toast.LENGTH_LONG).show();
            }
            break;
        }
    }
}
