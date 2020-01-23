package com.cinema.client.utils.notification;

import android.app.PendingIntent;
import android.content.Context;

import androidx.core.app.NotificationCompat;

import com.cinema.client.R;

public class CustomNotification {


    public static NotificationCompat.Builder getNotification(
            Context context,
            String channelId,
            String title,
            String content,
            PendingIntent pendingIntent) {
        return new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.ic_ticket_accent)
                .setContentTitle(title)
                .setContentText(content)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

    }


}
