package com.example.vadim.androidmesseger.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Vadim Denisov on 05/11/17.
 */

public class FriendListDBHelper extends SQLiteOpenHelper {
    /* Database constants */
    public static final String DATABASE_NAME = "AndroidMessenger.db";
    public static final int DATABASE_VERSION = 1;
    /* Table and column constants */
    public static final String TABLE_NAME = "FriendList";
    public static final String COLUMN_USER_ID   = "user_id";
    public static final String COLUMN_FRIEND_ID = "friend_id";

    public FriendListDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = String.format("create table %s (%s integer, %s integer);",
                TABLE_NAME, COLUMN_USER_ID, COLUMN_FRIEND_ID);
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //TODO Write upgrade code
    }


}