package com.example.vadim.androidmesseger.models;

/**
 * Created by Vadim Denisov on 28/11/17.
 */

public class ChatMessageModel {
    String text;
    String user;

    public ChatMessageModel() {}

    public ChatMessageModel(String user, String text) {
        this.user = user;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
