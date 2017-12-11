package com.example.vadim.androidmesseger.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.vadim.androidmesseger.R;
import com.example.vadim.androidmesseger.activities.MainActivity;
import com.example.vadim.androidmesseger.models.ChatMessageModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;


public class MessageNotificationService extends Service {
    final static String TAG = "MessageNotificationServiceTag";
    private NotificationManager notifManager;
    private boolean isInit = true;

    @Override
    public void onCreate() {
        notifManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        final int NOTIFY_ID = 1003;
        String name = "my_package_channel";
        String id = "my_package_channel_1";
        String description = "my_package_first_channel";

        NotificationCompat.Builder builder;

        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel mChannel = new NotificationChannel(id, name, importance);
        mChannel.setDescription(description);
        notifManager.createNotificationChannel(mChannel);

        builder = new NotificationCompat.Builder(this, id);

        builder.setContentTitle("AndroindMesseger notivication is active")
                .setSmallIcon(android.R.drawable.ic_popup_reminder)
                .setContentText(this.getString(R.string.app_name))
                .setDefaults(Notification.DEFAULT_ALL);

        Notification notification = builder.build();
        notifManager.notify(NOTIFY_ID, notification);
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        final DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            databaseRef.child("Users").child(user.getUid()).child("messages").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (isInit) {
                        isInit = false;
                        return;
                    }

                    for (DataSnapshot uid : dataSnapshot.getChildren()){
                        ChatMessageModel msg = null;

                        Iterator<DataSnapshot> iter = uid.getChildren().iterator();
                        while (iter.hasNext()) {
                            DataSnapshot item = iter.next();
                            Log.d(TAG, item.toString());
                            msg = item.getValue(ChatMessageModel.class);
                        }

                        if (msg != null && !msg.getUser().equals(user.getEmail())) {
                            showNotification(msg.getUser(), msg.getText());
                        }
                    }
                }

              @Override
                public void onCancelled(DatabaseError databaseError) {}
            });
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        notifManager.cancel(1);
        Toast.makeText(this, "Message notification service stopped", Toast.LENGTH_SHORT).show();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void showNotification(String title, String message) {
        final int NOTIFY_ID = 1002;

        String name = "my_package_channel";
        String id = "my_package_channel_1";
        String description = "my_package_first_channel";

        Intent intent;
        PendingIntent pendingIntent;
        NotificationCompat.Builder builder;

        if (notifManager == null) {
            notifManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        }

        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel mChannel = new NotificationChannel(id, name, importance);
        mChannel.setDescription(description);
        notifManager.createNotificationChannel(mChannel);

        builder = new NotificationCompat.Builder(this, id);

        intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        builder.setContentTitle(title)
                .setSmallIcon(android.R.drawable.ic_popup_reminder)
                .setContentText(message)
                .setDefaults(Notification.DEFAULT_ALL)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setTicker(title);

        Notification notification = builder.build();
        notifManager.notify(NOTIFY_ID, notification);
    }
}
