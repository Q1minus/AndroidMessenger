package com.example.vadim.androidmesseger.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

/**
 * Created by Vadim Denisov on 05/11/17.
 */

public class UserDBHelper extends SQLiteOpenHelper {
    /* Database constants */
    public static final String DATABASE_NAME = "AndroidMessenger.db";
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
        String sql = String.format("create table %s(%s integer primary key autoincrement, %s text, %s text, %s text, %s integer);",
                TABLE_NAME, COLUMN_ID, COLUMN_USERNAME, COLUMN_EMAIL, COLUMN_PASSWORD, COLUMN_FRIEND_LIST_ID);
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //TODO Write upgrade code
    }

}
