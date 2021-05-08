package com.example.office_task_mangement;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.widget.RemoteViews;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import java.io.Serializable;
public class MyBroadCastReceiver extends BroadcastReceiver implements Serializable {
    MediaPlayer mp;
    @Override
    public void onReceive(Context context, Intent intent) {
        mp = MediaPlayer.create(context, R.raw.action_epic);
        mp.start();
        Toast.makeText(context, "Alarm....", Toast.LENGTH_LONG).show();
        String channel_id2 ="ch1";
        String name = "akshat";
         channel_id2= (String)intent.getSerializableExtra("key1");
        name = (String)intent.getSerializableExtra("key2");
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel notificationChannel = null;
        String channel_id="akshat";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = new NotificationChannel(channel_id,name,importance);
        };
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),R.layout.custom_notification);
        remoteViews.setTextViewText(R.id.title,channel_id2);
        remoteViews.setTextViewText(R.id.text,name);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(R.drawable.ic_baseline_notifications_active_24);
        builder.setContentTitle(channel_id2);
        builder.setContentText(name);
        builder.setContent(remoteViews);
        builder.setPriority(Notification.PRIORITY_MAX);
        builder.setChannelId(channel_id);
        builder.build();
        // Add as notification
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            manager.createNotificationChannel(notificationChannel);

        }
        manager.notify(0, builder.build());
    }

}