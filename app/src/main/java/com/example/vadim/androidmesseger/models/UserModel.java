package com.example.vadim.androidmesseger.models;


import android.content.Intent;
import android.util.Log;

/**
 * Created by Vadim Denisov on 11/11/17.
 */

public class UserModel {
    public static final String ID_KEY = "id";
    public static final String USERNAME_KEY = "username";
    public static final String EMAIL_KEY = "email";

    long id;
    String username;
    String email;

    public UserModel(long id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }

    public UserModel(Intent intent) {
        this.id = intent.getExtras().getLong(ID_KEY);
        this.username =  intent.getExtras().getString(USERNAME_KEY);
        this.email = intent.getExtras().getString(EMAIL_KEY);
    }

    public long getId() { return id; }
    public String getUsername() {
        return username;
    }
    public String getEmail() {
        return email;
    }

    public void putExtraUser(Intent intent) {
        intent.putExtra(ID_KEY, id);
        intent.putExtra(USERNAME_KEY, username);
        intent.putExtra(EMAIL_KEY, email);
    }
}
