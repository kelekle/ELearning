package com.star.e_learning;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.star.e_learning.ui.activity.HomeActivity;

import static android.content.Context.NOTIFICATION_SERVICE;

public class PollingReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Bundle bundle=intent.getExtras();
        String description = bundle.getString("description");
        System.out.println("description: " + description);
        createNotification(context, description);
    }

    public void createNotification(Context context, String description){
        System.out.println("notification : ");
//        Intent intent = new Intent(context, HomeActivity.class);
//        // Create the TaskStackBuilder and add the intent, which inflates the back stack
//        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
//        stackBuilder.addNextIntentWithParentStack(intent);
//        // Get the PendingIntent containing the entire back stack
//        PendingIntent pendingIntent =
//                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
//        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, "home")
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .setContentTitle("New notification")
//                .setContentText(description)
//                .setStyle(new NotificationCompat.BigPictureStyle()
//                        .bigPicture(BitmapFactory.decodeResource(context.getResources(), R.mipmap.math_course, null)))
//                .setContentIntent(pendingIntent)
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//                .setAutoCancel(true);
//
//        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
//        notificationManager.notify(2, mBuilder.build());
        String id = "my_channel_01";
        String name="我是渠道名字";
        Intent intent = new Intent(context, HomeActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent, 0);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        Notification notification = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(id, name, NotificationManager.IMPORTANCE_LOW);
            notificationManager.createNotificationChannel(mChannel);
            notification = new Notification.Builder(context)
                    .setChannelId(id)
                    .setSmallIcon(R.mipmap.a)
                .setContentTitle("New notification")
                .setContentText(description)
                    .setContentIntent(pIntent)
                    .build();
        } else {
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                    .setContentTitle("New notification")
                    .setContentText(description)
                    .setSmallIcon(R.mipmap.a)
                    .setOngoing(true);
            notification = notificationBuilder.build();
        }
        notificationManager.notify(111123, notification);

    }

}
