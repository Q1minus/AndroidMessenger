package com.example.vadim.androidmesseger.fragments;

import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vadim.androidmesseger.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Query;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class UserInfoFragment extends DialogFragment implements View.OnTouchListener{
    public static final String KEY_FRIEND_POSITION = "friendPosition";
    public static final String KEY_FRIEND_LIST_IDS = "friend_list_ids";
    public static final int MIN_DISTANCE           = 150;

    ImageView friendsPhoto;
    TextView friendsEmail;

    FirebaseUser user;
    private float x1, x2;
    private ArrayList<String> friendsUid;
    private int position;

    private FirebaseAuth mAuth;
    private DatabaseReference myRef;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        position = getArguments().getInt("position");
        friendsUid = getArguments().getStringArrayList("uids");

        mAuth = FirebaseAuth.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference();
        user = mAuth.getCurrentUser();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_info, null);

        friendsPhoto = view.findViewById(R.id.Photo);
        friendsEmail = view.findViewById(R.id.Email);

        myRef.child("Users").child(friendsUid.get(position)).child("email")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        friendsEmail.setText(dataSnapshot.getValue(String.class));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) { }
                });

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
                    //currentFriendPosition++;
                    //if (currentFriendPosition >= usersFriendIds.size())
                    //    currentFriendPosition = 0;
                }
                /* Left swipe */
                else if (deltaX < 0) {
                    //currentFriendPosition--;
                    //if (currentFriendPosition < 0)
                    //    currentFriendPosition = usersFriendIds.size()-1;
                }
                //setupFriend();
            }
            break;
        }

        return true;
    }

}
