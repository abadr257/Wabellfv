package com.clixifi.wabell.services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.clixifi.wabell.R;
import com.clixifi.wabell.ui.main.MainScreen;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        try {
            Log.e(TAG, "title " + remoteMessage.getData().get("title").toString());
            Log.e(TAG, "body " + remoteMessage.getData().get("body").toString());
            Log.e(TAG, "type " + remoteMessage.getData().get("type").toString());

            showNotification(remoteMessage.getData().get("title").toString(), remoteMessage.getData().get("body").toString());
        } catch (Exception e) {

        }


    }


    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);


    }


    public void showNotification( String title, String body ) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        int notificationId = 1;
        String channelId = "channel-01";
        String channelName = "Channel Name";
        int importance = NotificationManager.IMPORTANCE_HIGH;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    channelId, channelName, importance);
            notificationManager.createNotificationChannel(mChannel);
        }
        Uri alarmSound = RingtoneManager
                .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        long pattern[] = {0, 800, 200, 300, 400};

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.wabell_logode)

                .setContentTitle(title)
                .setContentText(body)
                .setSound(alarmSound)
                .setAutoCancel(true)
                .setVibrate(pattern);

        Intent resultIntent = new Intent(this, MainScreen.class);


        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainScreen.class);
        stackBuilder.addNextIntent(resultIntent);

        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(
                0,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
        mBuilder.setContentIntent(resultPendingIntent);

        notificationManager.notify(notificationId, mBuilder.build());
    }
    private void sendNotification(String messageTitle, String messageBody  ) {
        /// o
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                this);

        mBuilder.setContentTitle(messageTitle);
        mBuilder.setContentText(messageBody);
        mBuilder.setStyle(new NotificationCompat.BigTextStyle()
                .bigText(messageBody));
        mBuilder.setTicker("");
        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
        mBuilder.setAutoCancel(true);
        long pattern[] = {0, 800, 200, 300, 400};
        mBuilder.setVibrate(pattern);
        Uri alarmSound = RingtoneManager
                .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        mBuilder.setSound(alarmSound);
        try {
            //  Log.e("pushnotification","1  "+type);
            Intent resultIntent = new Intent(this, MainScreen.class);

            TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
            stackBuilder.addParentStack(MainScreen.class);
            stackBuilder.addNextIntent(resultIntent);
            PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            mBuilder.setContentIntent(resultPendingIntent);
            NotificationManager mNotificationManager = (NotificationManager) this
                    .getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.notify( 1,
                    mBuilder.build());

        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
