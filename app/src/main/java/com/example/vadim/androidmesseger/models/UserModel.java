package com.example.vadim.androidmesseger.models;

/**
 * Created by Vadim Denisov on 11/11/17.
 */

public class UserModel {
    String username;
    String email;

    public UserModel(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

}
