package com.example.vadim.androidmesseger.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vadim.androidmesseger.R;
import com.example.vadim.androidmesseger.models.ChatMessageModel;
import com.example.vadim.androidmesseger.models.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class AddChatActivity extends AppCompatActivity implements View.OnClickListener{
    Button buttonAdd;
    EditText emailEdit;

    private FirebaseAuth mAuth;
    private DatabaseReference myRef;

    FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_chat);

        buttonAdd = findViewById(R.id.AddButton);
        emailEdit = findViewById(R.id.AddChatEmailField);

        buttonAdd.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference();

        user = mAuth.getCurrentUser();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("email", emailEdit.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedState) {
        super.onRestoreInstanceState(savedState);

        emailEdit.setText(savedState.getString("email"));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        case R.id.AddButton:
            String email = emailEdit.getText().toString().trim();

            myRef.child("Users").orderByChild("email").equalTo(email)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for ( DataSnapshot ds : dataSnapshot.getChildren()) {
                                UserModel friend = ds.getValue(UserModel.class);

                                //TODO add checks if chat already exists
                                if (friend != null ) {
                                    myRef.child("Users").child(user.getUid()).child("friends").push().setValue(friend.getId());
                                    Toast.makeText(AddChatActivity.this, String.format("User %s added", friend.getEmail()), Toast.LENGTH_LONG).show();
                                    finish();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) { }
                    });

            break;
        }
    }
}
