package com.example.vadim.androidmesseger.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Vadim Denisov on 05/11/17.
 */

public class FriendListTableDBHelper extends SQLiteOpenHelper {
    /* Database constants */
    public static final String NAME = "AndroidMessengerDB";
    public static final int VERSION = 1;

    /* TODO Table like this:

             FriendList
       +---------+-----------+
       | user_id | friend_id |
       +---------+-----------+
       |    1    |     33    |
       +---------+-----------+
       |    33   |     1     |
       +---------+-----------+
       .         .           .
       .         .           .
       .         .           .
       +---------+-----------+

       ========= OR ==========

       +---------+---------------------+
       | user_id | friends_id          |
       +---------+---------------------+
       |    1    |     [33, 28, 11]    |
       +---------+---------------------+
       |    33   |     [1, 4]          |
       +---------+---------------------+
       .         .                     .
       .         .                     .
       .         .                     .
       +---------+---------------------+

    */

    public FriendListTableDBHelper(Context context) {
        super(context, NAME, null, VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Write create code
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //TODO Write upgrade code
    }


}
