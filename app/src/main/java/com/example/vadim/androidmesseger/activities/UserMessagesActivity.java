package com.example.vadim.androidmesseger.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.vadim.androidmesseger.R;
import com.example.vadim.androidmesseger.adapters.UserAdapter;
import com.example.vadim.androidmesseger.fragments.UserInfoFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;


public class UserMessagesActivity extends AppCompatActivity implements View.OnClickListener{
    static final String FRAGMENT_TAG                = "user_info";

    static final String CONTEXT_MENU_ITEM_VIEW      = "View";
    static final String CONTEXT_MENU_ITEM_CALL      = "Call";
    static final String CONTEXT_MENU_ITEM_MESSAGE   = "Message";
    static final String CONTEXT_MENU_ITEM_EDIT      = "Edit";
    static final String CONTEXT_MENU_ITEM_REMOVE    = "Remove";

    Button buttonAddChat;
    TextView emailView;
    ListView listView;
    ProgressBar progressBar;

    UserAdapter userAdapter;
    UserInfoFragment userInfoFragment;
    FirebaseUser user;

    private FirebaseAuth mAuth;
    private DatabaseReference myRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_messages);

        userInfoFragment = new UserInfoFragment();
        mAuth = FirebaseAuth.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference();
        user = mAuth.getCurrentUser();
        emailView = findViewById(R.id.current_email);

        progressBar = findViewById(R.id.progressBar);
        buttonAddChat = findViewById(R.id.AddChatButton);
        listView = findViewById(R.id.chat_list);

        buttonAddChat.setOnClickListener(this);

        emailView.setText(user.getEmail());

        Query query = myRef.child("Users").child(user.getUid()).child("friends");
        userAdapter = new UserAdapter(this, String.class, R.layout.user_item, query, progressBar);

        listView.setAdapter(userAdapter);
        registerForContextMenu(listView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        super.onCreateContextMenu(contextMenu, view, contextMenuInfo);
        contextMenu.add(0, view.getId(), 0, CONTEXT_MENU_ITEM_VIEW);
        contextMenu.add(0, view.getId(), 0, CONTEXT_MENU_ITEM_CALL);
        contextMenu.add(0, view.getId(), 0, CONTEXT_MENU_ITEM_MESSAGE);
        contextMenu.add(0, view.getId(), 0, CONTEXT_MENU_ITEM_EDIT);
        contextMenu.add(0, view.getId(), 0, CONTEXT_MENU_ITEM_REMOVE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        case R.id.AddChatButton:
            Intent intent = new Intent(this, AddChatActivity.class);
            startActivity(intent);
            break;
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        boolean result = true;
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();

        switch (item.getTitle().toString()) {
        case CONTEXT_MENU_ITEM_VIEW:
            Bundle bundle = new Bundle();

            bundle.putStringArrayList("uids", userAdapter.getItems());
            bundle.putInt("position", info.position);

            userInfoFragment.setArguments(bundle);
            userInfoFragment.show(getFragmentManager(), FRAGMENT_TAG);
            break;
        case CONTEXT_MENU_ITEM_CALL:
            // TODO Call friend
            break;
        case CONTEXT_MENU_ITEM_MESSAGE:
            // TODO Open chat with friend
            break;
        case CONTEXT_MENU_ITEM_EDIT:
            // TODO Edit friend's info
            break;
        case CONTEXT_MENU_ITEM_REMOVE:
            // TODO Remove from friend list
            break;
        default:
            result = super.onContextItemSelected(item);
            break;
        }
        return result;
    }

    /*@Override
    protected void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);
        // TODO Open ChatActivity
    }*/ 
    
}
