package com.example.vadim.androidmesseger.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

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


public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView photoImage;
    Button buttonConfirm;
    EditText editEmail, editUsername;

    private FirebaseUser user;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        buttonConfirm = findViewById(R.id.confirm_button_profile);
        editEmail = findViewById(R.id.email_profile);
        editUsername = findViewById(R.id.username_profile);
        photoImage = findViewById(R.id.photo_profile);

        myRef = FirebaseDatabase.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();

        buttonConfirm.setOnClickListener(this);

        updateUI();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        case R.id.confirm_button_profile:
            String stringUsername = editUsername.getText().toString().trim();
            if (!stringUsername.isEmpty()) {
                myRef.child("Users").child(user.getUid()).child("username").setValue(stringUsername);
            }

            String stringEmail = editEmail.getText().toString().trim();
            if (!stringEmail.isEmpty()){
                myRef.child("Users").child(user.getUid()).child("email").setValue(stringEmail);
            }

            if (!stringEmail.isEmpty())
                Toast.makeText(ProfileActivity.this, "Done!", Toast.LENGTH_SHORT).show();
            break;
        }
    }

    private void updateUI() {
        myRef.child("Users").child(user.getUid()).child("email")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        editEmail.setText(dataSnapshot.getValue(String.class));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) { }
                });

        myRef.child("Users").child(user.getUid()).child("username")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        editUsername.setText(dataSnapshot.getValue(String.class));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) { }
                });

        final StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        myRef.child("Users").child(user.getUid()).child("photo")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String imageName = dataSnapshot.getValue(String.class);
                        if (imageName != null){
                            Glide.with(ProfileActivity.this)
                                    .using(new FirebaseImageLoader())
                                    .load(storageRef.child("images").child(imageName))
                                    .into(photoImage);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) { }
                });
    }
}
