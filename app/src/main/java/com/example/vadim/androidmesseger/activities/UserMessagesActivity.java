package com.example.vadim.androidmesseger.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.vadim.androidmesseger.R;
import com.example.vadim.androidmesseger.adapters.UserAdapter;
import com.example.vadim.androidmesseger.fragments.UserInfoFragment;
import com.example.vadim.androidmesseger.services.MessageNotificationService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;


public class UserMessagesActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    static final String FRAGMENT_TAG                = "user_info";

    static final String CONTEXT_MENU_ITEM_VIEW      = "View";
    static final String CONTEXT_MENU_ITEM_EDIT      = "Edit";
    static final String CONTEXT_MENU_ITEM_REMOVE    = "Remove";

    TextView emailView;
    Button buttonAddChat, buttonEditProfile;
    ListView listView;

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

        buttonAddChat = findViewById(R.id.AddChatButton);
        listView = findViewById(R.id.chat_list);
        buttonEditProfile = findViewById(R.id.edit_profile_btn);

        buttonAddChat.setOnClickListener(this);
        buttonEditProfile.setOnClickListener(this);
        emailView.setText(user.getEmail());

        Query query = myRef.child("Users").child(user.getUid()).child("friends");
        userAdapter = new UserAdapter(this, String.class, R.layout.user_item, query);

        listView.setAdapter(userAdapter);
        listView.setOnItemClickListener(this);
        registerForContextMenu(listView);

        startService(new Intent(this, MessageNotificationService.class));
    }

    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        super.onCreateContextMenu(contextMenu, view, contextMenuInfo);
        contextMenu.add(0, view.getId(), 0, CONTEXT_MENU_ITEM_VIEW);
        contextMenu.add(0, view.getId(), 0, CONTEXT_MENU_ITEM_EDIT);
        contextMenu.add(0, view.getId(), 0, CONTEXT_MENU_ITEM_REMOVE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        case R.id.AddChatButton:
            startActivity(new Intent(this, AddChatActivity.class));
            break;
        case R.id.edit_profile_btn:
            startActivity(new Intent(this, ProfileActivity.class));
            break;
        }
    }

    @Override
    public boolean onContextItemSelected(final MenuItem item) {
        boolean result = true;
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();

        switch (item.getTitle().toString()) {
        case CONTEXT_MENU_ITEM_VIEW:
            Bundle bundle = new Bundle();

            bundle.putStringArrayList("uids", userAdapter.getItems());
            bundle.putInt("position", info.position);
            updateUI(info.position);

            userInfoFragment.setArguments(bundle);
            userInfoFragment.show(getFragmentManager(), FRAGMENT_TAG);
            break;
        case CONTEXT_MENU_ITEM_EDIT:
            // TODO Edit friend's info
            break;
        case CONTEXT_MENU_ITEM_REMOVE:
            final String removableUid = userAdapter.getItem(info.position);

            myRef.child("Users").child(user.getUid()).child("friends").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for (DataSnapshot friendUid : dataSnapshot.getChildren()) {
                        String uid = friendUid.getValue(String.class);

                        if (removableUid.equals(uid)) {
                            friendUid.getRef().removeValue();
                            break;
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            break;
        default:
            result = super.onContextItemSelected(item);
            break;
        }
        return result;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        String friendUid = userAdapter.getItem(i);

        Intent intent = new Intent(this, ChatActivity.class);
        intent.putExtra("uid", friendUid);
        startActivity(intent);
    }

    private void updateUI(int position) {
        ArrayList<String> friends = userAdapter.getItems();
        String clickedFriend = userAdapter.getItem(position);

        friends.remove(position);
        friends.add(0, clickedFriend);
        myRef.child("Users").child(user.getUid()).child("friends").setValue(friends);
    }
}
