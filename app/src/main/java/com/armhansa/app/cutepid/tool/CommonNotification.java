package com.armhansa.app.cutepid.tool;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;

import com.armhansa.app.cutepid.controller.HomeActivity;
import com.armhansa.app.cutepid.R;

public class CommonNotification {

    //Singleton
    private static CommonNotification notiInstance;

    private Context context;

    private int count = 0;

    NotificationManager notificationManager;
    Notification notification;

    private String title;
    private String message;
    private int image;

    public static CommonNotification getInstance(Context context, String title) {
        if(notiInstance == null)
            notiInstance = new CommonNotification(context, title);

        return notiInstance;
    }

    private CommonNotification (Context context, String title) {
        this.context = context;
    }

    public void notify(String text) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);

        Intent intent = new Intent(context, HomeActivity.class);
//        intent.putExtra("message", MESSAGE);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(HomeActivity.class);
        stackBuilder.addNextIntent(intent);
        PendingIntent pendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new Notification.Builder(context)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("You have new match! Tab to chat with her.")
                .setContentText(text)
                .setLights(Color.argb(255, 255, 0, 100), 500, 2000)
                .setVibrate(new long[]{100, 500, 100, 200})
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentIntent(pendingIntent)
                .build();

        notificationManager.notify(count++, notification);
    }



}
