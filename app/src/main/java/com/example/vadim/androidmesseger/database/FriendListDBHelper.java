package com.example.vadim.androidmesseger.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.vadim.androidmesseger.models.UserModel;

import java.util.ArrayList;

/**
 * Created by Vadim Denisov on 05/11/17.
 */

public class FriendListDBHelper extends SQLiteOpenHelper {
    /* Database constants */
    public static final String DATABASE_NAME = "FriendList.db";
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
        String sql = "create table " + TABLE_NAME + "(" +
                COLUMN_USER_ID +    " integer, " +
                COLUMN_FRIEND_ID +  " integer);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //TODO Write upgrade code
    }

    public long addFriend(long userId, long friendId) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_USER_ID, userId);
        contentValues.put(COLUMN_FRIEND_ID, friendId);

        return database.insert(TABLE_NAME, null, contentValues);
    }

    public ArrayList<Long> getFriendsId(long id) {
        SQLiteDatabase database = this.getReadableDatabase();

        String[] columns = { COLUMN_FRIEND_ID };
        String whereCondition = COLUMN_USER_ID + "=?";
        String[] whereArguments = { String.valueOf(id) };

        Cursor cursor = database.query(
                TABLE_NAME, columns,
                whereCondition, whereArguments,
                null, null, null
        );

        ArrayList<Long> friendsId = new ArrayList<>();
        try {
            int idFriendColumnIndex = cursor.getColumnIndex(COLUMN_FRIEND_ID);

            while (cursor.moveToNext()) {
                long currentId = cursor.getInt(idFriendColumnIndex);
                friendsId.add(currentId);
            }
        } finally {
            cursor.close();
        }

        return friendsId;
    }

    public int removeFriend(long userId, long friendId) {
        SQLiteDatabase database = getWritableDatabase();

        String whereCondition = COLUMN_USER_ID + "=? AND " + COLUMN_FRIEND_ID + "=?";
        String[] whereArguments = { String.valueOf(userId), String.valueOf(friendId) };

        return database.delete(TABLE_NAME, whereCondition, whereArguments);
    }
}
