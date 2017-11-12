package com.example.vadim.androidmesseger.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.vadim.androidmesseger.R;
import com.example.vadim.androidmesseger.adapters.UserAdapter;
import com.example.vadim.androidmesseger.database.FriendListDBHelper;
import com.example.vadim.androidmesseger.database.UserDBHelper;
import com.example.vadim.androidmesseger.models.UserModel;

import java.util.ArrayList;


public class UserMessagesActivity extends AppCompatActivity implements View.OnClickListener {
    Button buttonAddChat;
    UserModel user;
    UserAdapter userAdapter;
    FriendListDBHelper friendListDBHelper;
    UserDBHelper userDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_messages);

        buttonAddChat = findViewById(R.id.AddChatButton);
        buttonAddChat.setOnClickListener(this);

        friendListDBHelper = new FriendListDBHelper(this);
        userDBHelper = new UserDBHelper(this);

        user = new UserModel(getIntent());

        // TODO Take out this logic
        ArrayList<UserModel> users = new ArrayList<>();
        for (Long id : friendListDBHelper.getFriendsId(user.getId())) {
            users.add(userDBHelper.findUser(id));
        }

        userAdapter = new UserAdapter(this, users);

        ListView chatList = findViewById(R.id.ChatList);
        chatList.setAdapter(userAdapter);
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
