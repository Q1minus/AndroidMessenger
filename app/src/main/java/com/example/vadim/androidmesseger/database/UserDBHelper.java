package com.example.vadim.androidmesseger.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import com.example.vadim.androidmesseger.models.UserModel;

import java.util.Currency;

/**
 * Created by Vadim Denisov on 05/11/17.
 */

public class UserDBHelper extends SQLiteOpenHelper {
    /* Database constants */
    public static final String DATABASE_NAME = "User.db";
    public static final int DATABASE_VERSION = 1;
    /* Table and column constants */
    public static final String TABLE_NAME = "User";
    public static final String COLUMN_ID       = "id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_EMAIL    = "email";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_FRIEND_LIST_ID = "friend_list_id";

    public UserDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table " + TABLE_NAME + "(" +
                COLUMN_ID +             " integer primary key autoincrement, " +
                COLUMN_USERNAME +       " text, " +
                COLUMN_EMAIL +          " text, " +
                COLUMN_PASSWORD +       " text);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //TODO Write upgrade code
    }

    public boolean isExistsUsername(String username) {
        SQLiteDatabase database = this.getReadableDatabase();

        String[] columns = { COLUMN_USERNAME };
        String whereCondition = COLUMN_USERNAME + "=?";
        String[] whereArguments = { username };

        Cursor cursor = database.query(
                TABLE_NAME, columns,
                whereCondition, whereArguments,
                null, null, null
        );

        boolean isExist = false;
        try {
            isExist = cursor.moveToFirst();
        } finally {
            cursor.close();
        }

        return isExist;
    }

    public boolean isExistsEmail(String email) {
        SQLiteDatabase database = this.getReadableDatabase();

        String[] columns = { COLUMN_EMAIL };
        String whereCondition = COLUMN_EMAIL + "=?";
        String[] whereArguments = { email };

        Cursor cursor = database.query(
                TABLE_NAME, columns,
                whereCondition, whereArguments,
                null, null, null
        );

        boolean isExist = false;
        try {
            isExist = cursor.moveToFirst();
        } finally {
            cursor.close();
        }

        return isExist;
    }

    public long addUser(String username, String email, String password) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_USERNAME, username);
        contentValues.put(COLUMN_EMAIL, email);
        contentValues.put(COLUMN_PASSWORD, password);

        return database.insert(TABLE_NAME, null, contentValues);
    }

    public UserModel findUser(String username, String email) {
        SQLiteDatabase database = this.getReadableDatabase();

        String[] columns = { COLUMN_ID, COLUMN_USERNAME, COLUMN_EMAIL };
        String whereCondition = COLUMN_EMAIL + "=? OR " + COLUMN_USERNAME + "=?";
        String[] whereArguments = { email, username };


        Cursor cursor = database.query(
                TABLE_NAME, columns,
                whereCondition, whereArguments,
                null, null, null
        );

        UserModel user = null;
        try {
            if (cursor.moveToFirst()) {
                int idColumnIndex = cursor.getColumnIndex(COLUMN_ID);
                int usernameColumnIndex = cursor.getColumnIndex(COLUMN_USERNAME);
                int emailColumnIndex = cursor.getColumnIndex(COLUMN_EMAIL);

                int currentId = cursor.getInt(idColumnIndex);
                String currentUsername = cursor.getString(usernameColumnIndex);
                String currentEmail = cursor.getString(emailColumnIndex);

                user = new UserModel(currentId, currentUsername, currentEmail);
            }
        } finally {
            cursor.close();
        }

        return user;
    }
    public UserModel findUser(long id) {
        SQLiteDatabase database = this.getReadableDatabase();

        String[] columns = { COLUMN_ID, COLUMN_USERNAME, COLUMN_EMAIL };
        String whereCondition = COLUMN_ID + "=?";
        String[] whereArguments = { String.valueOf(id) };

        Cursor cursor = database.query(
                TABLE_NAME, columns,
                whereCondition, whereArguments,
                null, null, null
        );

        UserModel user = null;
        try {
            int idColumnIndex = cursor.getColumnIndex(COLUMN_ID);
            int usernameColumnIndex = cursor.getColumnIndex(COLUMN_USERNAME);
            int emailColumnIndex = cursor.getColumnIndex(COLUMN_EMAIL);

            if (cursor.moveToFirst()) {
                int currentId = cursor.getInt(idColumnIndex);
                String currentUsername = cursor.getString(usernameColumnIndex);
                String currentEmail = cursor.getString(emailColumnIndex);

                user = new UserModel(currentId, currentUsername, currentEmail);
            }
        } finally {
            cursor.close();
        }

        return user;
    }

    public UserModel Authentication(String username, String password) {
        SQLiteDatabase database = this.getReadableDatabase();
        Log.d("OK", "kk");
        String[] columns = { COLUMN_ID, COLUMN_USERNAME, COLUMN_EMAIL  };
        String whereCondition = COLUMN_USERNAME + "=? AND " + COLUMN_PASSWORD + "=?";
        String[] whereArguments = { username, password };

        Cursor cursor = database.query(
                TABLE_NAME, columns,
                whereCondition, whereArguments,
                null, null, null
        );

        UserModel user = null;
        try {
            int idColumnIndex = cursor.getColumnIndex(COLUMN_ID);
            int usernameColumnIndex = cursor.getColumnIndex(COLUMN_USERNAME);
            int emailColumnIndex = cursor.getColumnIndex(COLUMN_EMAIL);

            if (cursor.moveToFirst()) {
                int currentId = cursor.getInt(idColumnIndex);
                String currentUsername = cursor.getString(usernameColumnIndex);
                String currentEmail = cursor.getString(emailColumnIndex);

                user = new UserModel(currentId, currentUsername, currentEmail);
            }
        } finally {
            cursor.close();
        }

        return user;
    }


}
