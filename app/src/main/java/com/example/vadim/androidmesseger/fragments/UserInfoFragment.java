package com.example.vadim.androidmesseger.fragments;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vadim.androidmesseger.R;
import com.example.vadim.androidmesseger.database.UserDBHelper;
import com.example.vadim.androidmesseger.models.UserModel;

import java.util.ArrayList;


public class UserInfoFragment extends DialogFragment implements View.OnTouchListener{
    public static final String KEY_FRIEND_POSITION = "friendPosition";
    public static final String KEY_FRIEND_LIST_IDS = "friend_list_ids";
    public static final int MIN_DISTANCE           = 150;

    ImageView friendsPhoto;
    TextView friendsUsername, friendsEmail;
    UserDBHelper userDBHelper;

    ArrayList<Long> usersFriendIds;
    int currentFriendPosition;
    UserModel currentFriend;
    float x1, x2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userDBHelper = new UserDBHelper(this.getActivity());
        usersFriendIds = new ArrayList<>();
        long[] ids = getArguments().getLongArray(KEY_FRIEND_LIST_IDS);
        currentFriendPosition = getArguments().getInt(KEY_FRIEND_POSITION);

        if (ids != null) {
            for (long id : ids)
                usersFriendIds.add(id);
        } else {
            Toast.makeText(getActivity().getApplicationContext(), R.string.NullPointerFriends, Toast.LENGTH_LONG).show();
            getActivity().onBackPressed();
        }

        long currentFriendId = usersFriendIds.get(currentFriendPosition);
        currentFriend = userDBHelper.findUser(currentFriendId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_info, null);

        friendsPhoto = view.findViewById(R.id.Photo);
        friendsUsername = view.findViewById(R.id.Username);
        friendsEmail = view.findViewById(R.id.Email);

        friendsUsername.setText(currentFriend.getUsername());
        friendsEmail.setText(currentFriend.getEmail());

        view.setOnTouchListener(this);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        View view = this.getActivity().getWindow().getDecorView();

        Window window = this.getDialog().getWindow();
        window.setLayout(view.getWidth(), view.getHeight()/2);
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        switch (event.getAction()) {
        case MotionEvent.ACTION_DOWN:
            x1 = event.getX();
            break;
        case MotionEvent.ACTION_UP:
            x2 = event.getX();
            float deltaX = x2 - x1;

            if (Math.abs(deltaX) > MIN_DISTANCE) {
                /* Right swipe */
                if (deltaX > 0) {
                    currentFriendPosition++;
                    if (currentFriendPosition >= usersFriendIds.size())
                        currentFriendPosition = 0;
                }
                /* Left swipe */
                else if (deltaX < 0) {
                    currentFriendPosition--;
                    if (currentFriendPosition < 0)
                        currentFriendPosition = usersFriendIds.size()-1;
                }
                setupFriend();
            }
            break;
        }

        return true;
    }

    private void setupFriend() {
        long id = usersFriendIds.get(currentFriendPosition);
        currentFriend = userDBHelper.findUser(id);

        friendsUsername.setText(currentFriend.getUsername());
        friendsEmail.setText(currentFriend.getEmail());
    }
}
