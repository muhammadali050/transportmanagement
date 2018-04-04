package com.climesoftt.transportmanagement.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;

import com.climesoftt.transportmanagement.AdminDashboardActivity;

/**
 * Created by AtoZ on 3/30/2018.
 */

public class AlertDialogClass {
    public static void alertDialogue(final Context context)
    {
        new android.support.v7.app.AlertDialog.Builder(context)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to logout?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        Logout.logoutUser(context);
                    }
                }).create().show();
    }
}
