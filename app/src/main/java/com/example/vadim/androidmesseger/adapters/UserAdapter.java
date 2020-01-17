package com.example.vadim.androidmesseger.adapters;

import android.app.Activity;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.vadim.androidmesseger.R;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;


/**
 * Created by Vadim Denisov on 12/11/17.
 */

public class UserAdapter extends FirebaseListAdapter<String> {
    /**
     * @param activity    The activity containing the ListView
     * @param modelClass  Firebase will marshall the data at a location into
     *                    an instance of a class that you provide
     * @param modelLayout This is the layout used to represent a single list item.
     *                    You will be responsible for populating an instance of the corresponding
     *                    view with the data from an instance of modelClass.
     * @param ref         The Firebase location to watch for data changes. Can also be a slice of a location,
     *                    using some combination of {@code limit()}, {@code startAt()}, and {@code endAt()}.
     */

    public UserAdapter(Activity activity, Class<String> modelClass, int modelLayout, Query ref) {
        super(activity, modelClass, modelLayout, ref);
    }

    @Override
    protected void populateView(View view, String model, int position) {
        final TextView tv = view.findViewById(R.id.email);
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();

        myRef.child("Users").child(model).child("email").addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tv.setText(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });


        final ImageView iv = view.findViewById(R.id.photo);
        final StorageReference storageRef = FirebaseStorage.getInstance().getReference();

        myRef.child("Users").child(model).child("photo").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String imageName = dataSnapshot.getValue(String.class);

                if (imageName != null){
                    Glide.with(mActivity)
                            .using(new FirebaseImageLoader())
                            .load(storageRef.child("images").child(imageName))
                            .into(iv);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });
}

    public ArrayList<String> getItems() {
        ArrayList<String> items = new ArrayList<>();
        for (int i = 0; i < getCount(); i++) {
            items.add(getItem(i));
        }
        return items;
    }

}
