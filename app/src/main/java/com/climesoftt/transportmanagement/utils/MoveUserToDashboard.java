package com.climesoftt.transportmanagement.utils;

import android.content.Context;
import android.content.Intent;

import com.climesoftt.transportmanagement.AdminDashboardActivity;
import com.climesoftt.transportmanagement.DriverDashboard;
import com.climesoftt.transportmanagement.Personal;

public class MoveUserToDashboard {
    public static void moveUser(Context context,String userType)
    {
        if(userType.equals("Admin"))
        {
            Intent intent = new Intent(context, AdminDashboardActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intent);
        }else if(userType.equals("Driver"))
        {
            Intent intent = new Intent(context, DriverDashboard.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intent);
        }else if(userType.equals("Personal"))
        {
            Intent intent = new Intent(context, Personal.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intent);
        }
    }
}
