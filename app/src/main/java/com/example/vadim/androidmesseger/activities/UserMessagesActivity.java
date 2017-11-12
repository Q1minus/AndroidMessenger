package com.example.vadim.androidmesseger.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.vadim.androidmesseger.R;
import com.example.vadim.androidmesseger.models.UserModel;


public class UserMessagesActivity extends AppCompatActivity implements View.OnClickListener {
    Button buttonAddChat;
    UserModel user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_messages);

        buttonAddChat = findViewById(R.id.AddChatButton);
        buttonAddChat.setOnClickListener(this);
        user = new UserModel(getIntent());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        case R.id.AddChatButton:
            startAddChatActivity();
            break;
        }
    }

    private void startAddChatActivity() {
        Intent intent = new Intent(this, AddChatActivity.class);
        user.putExtraUser(intent);
        startActivity(intent);
    }
}
