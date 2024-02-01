package com.marzbani.adjoetask.infrastructure;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.marzbani.adjoetask.R;

public class NotificationUtil {

    public static void showNotification(Context mContext, String time) {
        NotificationManager mNotificationManager;
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(mContext.getApplicationContext(), Constants.NOTIFICATION_CHANNEL);
        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
        bigText.setBigContentTitle("User time "+time);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher_round);
        mBuilder.setContentTitle("User time");
        mBuilder.setContentText(time);
        mBuilder.setPriority(Notification.PRIORITY_MAX);
        mBuilder.setStyle(bigText);

        mNotificationManager =
                (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        createNotificationChannel(mContext);
        mNotificationManager.notify(123, mBuilder.build());

    }



    static private void createNotificationChannel(Context context) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is not in the Support Library.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(Constants.NOTIFICATION_CHANNEL, Constants.NOTIFICATION_CHANNEL, importance);
            channel.setDescription("Nothing!");

            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
