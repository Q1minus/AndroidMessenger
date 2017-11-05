package com.example.vadim.androidmesseger.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

/**
 * Created by Vadim Denisov on 05/11/17.
 */

public class UserTableDBHelper extends SQLiteOpenHelper {
    /* Database constants */
    public static final String NAME = "AndroidMessengerDB";
    public static final int VERSION = 1;
    /* Table and column constants */
    public static final String TABLE_NAME = "User";
    public static final String COLUMN_ID       = "id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_EMAIL    = "email";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_FRIEND_LIST_ID = "friend_list_id";

    public UserTableDBHelper(Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table " + TABLE_NAME + "(" +
                COLUMN_ID       + " integer primary key autoincrement," +
                COLUMN_USERNAME + " text," +
                COLUMN_EMAIL    + " text," +
                COLUMN_PASSWORD + " text," +
                COLUMN_FRIEND_LIST_ID + "integer);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //TODO Write upgrade code
    }

}
