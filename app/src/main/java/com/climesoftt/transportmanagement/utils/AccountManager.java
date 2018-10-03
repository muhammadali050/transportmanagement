package com.climesoftt.transportmanagement.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class AccountManager {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    public AccountManager(Context context)
    {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = sharedPreferences.edit();
    }

    public void saveUserCredentials(String name,String accountType, String email, String imgUrl)
    {
        editor.putString("USER_NAME", name);
        editor.putString("USER_TYPE", accountType);
        editor.putString("USER_EMAIL", email);
        editor.putString("USER_IMAGE", imgUrl);
        editor.commit();
    }

    public void deleteUserCredentials()
    {
        editor.clear();
        editor.commit();
    }

    public String getUserName()
    {
        String userName = sharedPreferences.getString("USER_NAME", "");
        return userName;
    }

    public String getUserAccountType()
    {
        String userAccountType = sharedPreferences.getString("USER_TYPE", "");
        return userAccountType;
    }

    public String getUserEmail()
    {
        String userEmail = sharedPreferences.getString("USER_EMAIL", "");
        return userEmail;
    }

    public String getUserImage() {
        String userImageUrl = sharedPreferences.getString("USER_IMAGE", "");
        return userImageUrl;
    }
}
