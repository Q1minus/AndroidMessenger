package com.example.vadim.androidmesseger.adapters;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.vadim.androidmesseger.R;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

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
    ProgressBar progressBar;

    public UserAdapter(Activity activity, Class<String> modelClass, int modelLayout, Query ref, ProgressBar progressBar) {
        super(activity, modelClass, modelLayout, ref);
        this.progressBar = progressBar;
    }

    @Override
    protected void populateView(View view, String model, int position) {
        final TextView tv = view.findViewById(R.id.email);
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();

        progressBar.setVisibility(View.VISIBLE);
        myRef.child("Users").child(model).child("email")
                .addValueEventListener( new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        tv.setText(dataSnapshot.getValue(String.class));
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) { }
                });

        tv.setText(model);
    }

    public ArrayList<String> getItems() {
        ArrayList<String> items = new ArrayList<>();
        for (int i = 0; i < getCount(); i++) {
            items.add(getItem(i));
        }
        return items;
    }

}
