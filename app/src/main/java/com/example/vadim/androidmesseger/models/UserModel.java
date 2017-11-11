package com.example.vadim.androidmesseger.models;

/**
 * Created by Vadim Denisov on 11/11/17.
 */

public class UserModel {
    String name, surname;
    String username;
    String email;

    public UserModel(String name, String surname, String username, String email) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

}
