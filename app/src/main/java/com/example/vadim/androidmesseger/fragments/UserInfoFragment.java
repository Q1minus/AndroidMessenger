package com.example.vadim.androidmesseger.fragments;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vadim.androidmesseger.R;
import com.example.vadim.androidmesseger.database.UserDBHelper;
import com.example.vadim.androidmesseger.models.UserModel;


public class UserInfoFragment extends DialogFragment {
    static final String KEY_FRIEND_ID = "friendId";

    ImageView friendsPhoto;
    TextView friendsUsername, friendsEmail;

    UserDBHelper userDBHelper;
    UserModel friend;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_info, null);
        long friendsId = getArguments().getLong(KEY_FRIEND_ID);

        userDBHelper = new UserDBHelper(this.getActivity());
        friend = userDBHelper.findUser(friendsId);

        friendsPhoto = view.findViewById(R.id.Photo);
        friendsUsername = view.findViewById(R.id.Username);
        friendsEmail = view.findViewById(R.id.Email);

        friendsUsername.setText(friend.getUsername());
        friendsEmail.setText(friend.getEmail());

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        View view = this.getActivity().getWindow().getDecorView();

        Window window = this.getDialog().getWindow();
        window.setLayout(view.getWidth(), view.getHeight()/2);
    }

}
