package com.example.vadim.androidmesseger.models;

/**
 * Created by Vadim Denisov on 11/11/17.
 */

public class UserModel {
    public String id;
    public String email;

    public UserModel() { }

    public UserModel(String id, String email) {
        this.email = email;
        this.id = id;
    }

    public String getId() {
        return id;
    }
    public String getEmail() {
        return email;
    }

}
