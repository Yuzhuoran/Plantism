package com.waterme.plantism.sync;

import android.support.v4.app.NotificationCompat;

/**
 * Created by minghe on 3/10/18.
 */

public class plantNotification {
    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.notification_icon)
            .setContentTitle("My notification")
            .setContentText("Hello World!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT);


}
