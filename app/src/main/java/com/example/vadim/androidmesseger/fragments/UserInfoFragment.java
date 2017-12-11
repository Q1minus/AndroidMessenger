package com.example.vadim.androidmesseger.fragments;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.vadim.androidmesseger.R;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;


public class UserInfoFragment extends DialogFragment implements View.OnTouchListener{
    public static final int MIN_DISTANCE           = 150;

    private float x1, x2, y1, y2;
    private ArrayList<String> friendsUid;
    private int position;

    private DatabaseReference myRef;

    ImageView friendsPhoto;
    Matrix matrix = new Matrix();
    Float scale = 1f;
    ScaleGestureDetector scaleGestureDetector;
    TextView friendsEmail;

    FirebaseUser user;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        position = getArguments().getInt("position");
        friendsUid = getArguments().getStringArrayList("uids");

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference();
        user = mAuth.getCurrentUser();

        scaleGestureDetector = new ScaleGestureDetector(getActivity(), new ScaleListener());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_info, null);

        friendsPhoto = view.findViewById(R.id.Photo);
        friendsEmail = view.findViewById(R.id.Email);

        updateUI();

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

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View view, MotionEvent event) {
        scaleGestureDetector.onTouchEvent(event);
        switch (event.getAction()) {
        case MotionEvent.ACTION_DOWN:
            x1 = event.getX();
            y1 = event.getY();
            break;
        case MotionEvent.ACTION_UP:
            x2 = event.getX();
            y2 = event.getY();
            float deltaX = x2 - x1;

            if (!(x1 <= 192 && y1 <= 192) && Math.abs(deltaX) > MIN_DISTANCE) {
                /* Right swipe */
                if (deltaX > 0) {
                    if (++position >= friendsUid.size())
                        position = 0;
                }
                /* Left swipe */
                else if (deltaX < 0) {
                    if (--position < 0)
                        position = friendsUid.size()-1;
                }
                updateUI();
            }

            break;
        }
        return true;
    }


    private void updateUI() {
        myRef.child("Users").child(friendsUid.get(position)).child("email")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        friendsEmail.setText(dataSnapshot.getValue(String.class));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) { }
                });

        final StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        myRef.child("Users").child(friendsUid.get(position)).child("photo")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String imageName = dataSnapshot.getValue(String.class);
                        if (imageName != null){
                            Glide.with(getActivity())
                                    .using(new FirebaseImageLoader())
                                    .load(storageRef.child("images").child(imageName))
                                    .into(friendsPhoto);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) { }
                });

    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            scale *= detector.getScaleFactor();

            matrix.setScale(scale, scale);
            friendsPhoto.setImageMatrix(matrix);
            return true;
        }
    }

}
