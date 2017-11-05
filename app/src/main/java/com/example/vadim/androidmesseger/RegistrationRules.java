package com.example.vadim.androidmesseger;

import android.util.Patterns;

/**
 * Created by vadim on 04/11/17.
 */

public final class RegistrationRules {
    public boolean isUsernameValid(String username) {
        return true;
    }

    public boolean isPasswordValid(String password) {
        if (password.length() >= 6) {
            return true;
        }
        return false;
    }

    public boolean isEmailValid(String email) {
        if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return true;
        }
        return false;
    }
}
