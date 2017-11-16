package com.example.vadim.androidmesseger.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    UserAdapter userAdapter;
    ListView chatList;

    FriendListDBHelper friendListDBHelper;
    UserDBHelper userDBHelper;

    UserModel currentUser;
    ArrayList<UserModel> usersFriends;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_messages);

        chatList = findViewById(R.id.ChatList);
        buttonAddChat = findViewById(R.id.AddChatButton);
        buttonAddChat.setOnClickListener(this);

        friendListDBHelper = new FriendListDBHelper(this);
        userDBHelper = new UserDBHelper(this);

        currentUser = new UserModel(getIntent());
        usersFriends = userDBHelper.getUsersFriend(currentUser, friendListDBHelper);

        userAdapter = new UserAdapter(this, usersFriends);
        chatList.setAdapter(userAdapter);
    }

    @Override
    public void onRestart() {
        super.onRestart();

        usersFriends = userDBHelper.getUsersFriend(currentUser, friendListDBHelper);
        userAdapter = new UserAdapter(this, usersFriends);
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
        currentUser.putExtraUser(intent);
        startActivity(intent);
    }
}
