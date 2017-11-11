package com.example.vadim.androidmesseger;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class UserMessagesActivity extends AppCompatActivity implements View.OnClickListener {
    Button buttonAddChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_messages);

        buttonAddChat = findViewById(R.id.AddChatButton);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.AddChatButton:
                // TODO
                break;
        }
    }
}
