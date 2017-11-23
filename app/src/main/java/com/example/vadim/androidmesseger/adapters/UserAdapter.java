package com.example.vadim.androidmesseger.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vadim.androidmesseger.R;
import com.example.vadim.androidmesseger.models.UserModel;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;


/**
 * Created by Vadim Denisov on 12/11/17.
 */

public class UserAdapter extends FirebaseListAdapter<UserModel> {

    public UserAdapter(FirebaseListOptions<UserModel> options) {
        super(options);
    }

    @Override
    protected void populateView(View view, UserModel model, int position) {
        TextView email = view.findViewById(R.id.email);
        ImageView photo = view.findViewById(R.id.photo);

        email.setText(model.getEmail());
        //TODO photo.setSrc = LoadImgFromServer
    }

}
