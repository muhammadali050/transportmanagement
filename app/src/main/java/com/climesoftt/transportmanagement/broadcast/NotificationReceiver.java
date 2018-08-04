package com.climesoftt.transportmanagement.broadcast;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.widget.Toast;

import com.climesoftt.transportmanagement.AllMaintenceActivity;
import com.climesoftt.transportmanagement.R;


public class NotificationReceiver extends BroadcastReceiver{

    public void onReceive(Context context, Intent intent) {

        Intent _myIntent = new Intent(context, AllMaintenceActivity.class);
        PendingIntent _myPendingIntent = PendingIntent.getActivity(context.getApplicationContext(), 0, _myIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, "maintainence")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Maintainence Required")
                .setContentText("abc".toUpperCase() + " " + "type".toUpperCase())
                .setStyle(new NotificationCompat.BigTextStyle().bigText("You have a Maintenance Today!!"))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(_myPendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        notificationManager.notify(0, mBuilder.build());


    }
}
