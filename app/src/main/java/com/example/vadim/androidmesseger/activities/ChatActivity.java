package com.example.vadim.androidmesseger.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.vadim.androidmesseger.R;
import com.example.vadim.androidmesseger.models.ChatMessageModel;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class ChatActivity extends AppCompatActivity {
    Button sendButton;
    EditText messageEdit;
    ListView messages;

    private FirebaseListAdapter<ChatMessageModel> mAdapter;
    private FirebaseUser user;
    private String friendUid;
    private DatabaseReference myRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        friendUid = getIntent().getStringExtra("uid");

        sendButton = findViewById(R.id.send_button);
        messageEdit = findViewById(R.id.message_edit);
        messages = findViewById(R.id.list_of_message);

        myRef = FirebaseDatabase.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChatMessageModel message = new ChatMessageModel(user.getEmail(), messageEdit.getText().toString());

                myRef.child("Users").child(user.getUid()).child("messages").child(friendUid).push().setValue(message);
                if (user.getUid() != friendUid){
                    myRef.child("Users").child(friendUid).child("messages").child(user.getUid()).push().setValue(message);
                }

                messageEdit.setText("");
                messageEdit.requestFocus();
            }
        });

        Query query = myRef.child("Users").child(user.getUid()).child("messages").child(friendUid);
        mAdapter = new FirebaseListAdapter<ChatMessageModel>(this, ChatMessageModel.class,
                R.layout.message_item, query) {
            @Override
            protected void populateView(View v, ChatMessageModel model, int position) {
                TextView messageText = v.findViewById(R.id.message_text);
                TextView messageUser = v.findViewById(R.id.message_user);

                messageText.setText(model.getText());
                messageUser.setText(model.getUser());
            }
        };

        messages.setAdapter(mAdapter);
    }

}
