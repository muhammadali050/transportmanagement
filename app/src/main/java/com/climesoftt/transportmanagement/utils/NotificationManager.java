package com.climesoftt.transportmanagement.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.climesoftt.transportmanagement.AllMaintenceActivity;
import com.climesoftt.transportmanagement.MainActivity;
import com.climesoftt.transportmanagement.broadcast.NotificationReceiver;

import java.util.Calendar;

public class NotificationManager {
    public static void setAlarm(Context context, Calendar calendar){

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent _myIntent = new Intent(context, NotificationReceiver.class);
        PendingIntent _myPendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(), 0, _myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),_myPendingIntent);

    }
}
