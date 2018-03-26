package com.climesoftt.transportmanagement.utils;

import android.content.Context;
import android.content.Intent;

import com.climesoftt.transportmanagement.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by AtoZ on 3/25/2018.
 */

public class Logout {
    public static void logoutUser(Context context)
    {
        FirebaseAuth.getInstance().signOut();
        context.startActivity(new Intent(context, LoginActivity.class));
    }
}
