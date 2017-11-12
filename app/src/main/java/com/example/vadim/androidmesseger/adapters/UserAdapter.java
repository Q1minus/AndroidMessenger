package com.example.vadim.androidmesseger.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vadim.androidmesseger.R;
import com.example.vadim.androidmesseger.models.UserModel;

import java.util.ArrayList;

/**
 * Created by Vadim Denisov on 12/11/17.
 */

public class UserAdapter extends BaseAdapter {
    Context context;
    ArrayList<UserModel> chatList;
    LayoutInflater layoutInflater;

    public UserAdapter(Context context, ArrayList<UserModel> users) {
        this.context = context;
        chatList = users;
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return chatList.size();
    }

    @Override
    public UserModel getItem(int i) {
        return chatList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return chatList.get(i).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.user_item, parent, false);
        }

        UserModel user = this.getItem(position);

        TextView username = view.findViewById(R.id.Username);
        username.setText(user.getUsername());

        ImageView photo = view.findViewById(R.id.Photo);
        //TODO photo.setSrc = LoadImgFromServer

        return view;
    }

}
