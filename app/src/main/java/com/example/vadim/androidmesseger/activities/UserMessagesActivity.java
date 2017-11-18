package com.example.vadim.androidmesseger.activities;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.vadim.androidmesseger.R;
import com.example.vadim.androidmesseger.adapters.UserAdapter;
import com.example.vadim.androidmesseger.database.FriendListDBHelper;
import com.example.vadim.androidmesseger.database.UserDBHelper;
import com.example.vadim.androidmesseger.fragments.UserInfoFragment;
import com.example.vadim.androidmesseger.models.UserModel;

import java.util.ArrayList;


public class UserMessagesActivity extends ListActivity implements View.OnClickListener{
    static final String FRAGMENT_TAG                = "user_info";

    static final String CONTEXT_MENU_ITEM_VIEW      = "View";
    static final String CONTEXT_MENU_ITEM_CALL      = "Call";
    static final String CONTEXT_MENU_ITEM_MESSAGE   = "Message";
    static final String CONTEXT_MENU_ITEM_EDIT      = "Edit";
    static final String CONTEXT_MENU_ITEM_REMOVE    = "Remove";

    Button buttonAddChat;
    UserAdapter userAdapter;
    UserInfoFragment userInfoFragment;

    FriendListDBHelper friendListDBHelper;
    UserDBHelper userDBHelper;

    UserModel currentUser;
    ArrayList<UserModel> usersFriends;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_messages);

        userInfoFragment = new UserInfoFragment();

        buttonAddChat = findViewById(R.id.AddChatButton);
        buttonAddChat.setOnClickListener(this);

        friendListDBHelper = new FriendListDBHelper(this);
        userDBHelper = new UserDBHelper(this);

        currentUser = new UserModel(getIntent());
        usersFriends = userDBHelper.getUsersFriend(currentUser, friendListDBHelper);

        userAdapter = new UserAdapter(this, usersFriends);
        this.setListAdapter(userAdapter);
        updateChatList();

        this.registerForContextMenu(this.getListView());
    }

    @Override
    public void onRestart() {
        super.onRestart();
        updateChatList();
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
            startAddChatActivity();
            break;
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        boolean result = true;
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo )item.getMenuInfo();

        switch (item.getTitle().toString()) {
        case CONTEXT_MENU_ITEM_VIEW:
            Bundle bundle = new Bundle();

            bundle.putInt(UserInfoFragment.KEY_FRIEND_POSITION, info.position);
            bundle.putLongArray(UserInfoFragment.KEY_FRIEND_LIST_IDS, UserModel.getIds(usersFriends));

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
            friendListDBHelper.removeFriend(currentUser.getId(), info.id);
            updateChatList();
            break;
        default:
            result = super.onContextItemSelected(item);
            break;
        }
        return result;
    }

    @Override
    protected void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);
        // TODO Open ChatActivity
    }

    private void updateChatList() {
        usersFriends = userDBHelper.getUsersFriend(currentUser, friendListDBHelper);
        userAdapter = new UserAdapter(this, usersFriends);
        setListAdapter(userAdapter);
    }

    private void startAddChatActivity() {
        Intent intent = new Intent(this, AddChatActivity.class);
        currentUser.putExtraUser(intent);
        startActivity(intent);
    }

}
