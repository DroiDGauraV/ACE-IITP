package com.ace.ace_iitp;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;
import java.util.Objects;
import java.util.Random;

@SuppressLint("MissingFirebaseInstanceTokenRefresh")
public class MyFirebaseInstanceService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseInstance";

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        if (remoteMessage.getData().isEmpty()) {
            showNotification(Objects.requireNonNull(remoteMessage.getNotification()).getTitle(),
                    remoteMessage.getNotification().getBody());
        } else {
            showNotification(remoteMessage.getData());
        }
        super.onMessageReceived(remoteMessage);
    }

    @Override
    public void onNewToken(@NonNull String token) {
        Log.d(TAG, "Refreshed token: " + token);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
    }
    private void showNotification(Map<String,String> data) {
        String title = data.get("title");
        String body = data.get("body");

        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFICATION_CHANNEL_ID = "example.ace_iitp.test";

        assert notificationManager != null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID,
                    "Notification", NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription("ACE-IITP");
            notificationManager.createNotificationChannel(notificationChannel);
        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this,
                NOTIFICATION_CHANNEL_ID);
        notificationBuilder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ic_small)
                .setContentTitle(title)
                .setContentText(body)
                .setContentInfo("This notification notifies about ACE-IITP");
        notificationManager.notify(new Random().nextInt(), notificationBuilder.build());
    }

    private void showNotification(String title, String body) {
        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFICATION_CHANNEL_ID = "example.ace_iitp.test";

        assert notificationManager != null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID,
                    "Notification", NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription("ACE-IITP");

            notificationManager.createNotificationChannel(notificationChannel);
        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this,
                NOTIFICATION_CHANNEL_ID);
        notificationBuilder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ic_small)
                .setContentTitle(title)
                .setContentText(body)
                .setContentInfo("This notification notifies about ACE-IITP");
        notificationManager.notify(new Random().nextInt(), notificationBuilder.build());
    }
}
