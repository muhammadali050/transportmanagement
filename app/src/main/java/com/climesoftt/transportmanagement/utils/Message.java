package com.climesoftt.transportmanagement.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by AtoZ on 3/19/2018.
 */

public class Message {

    public static void show(Context context, String mesg) {
        Toast.makeText(context, mesg, Toast.LENGTH_LONG).show();
    }
}
