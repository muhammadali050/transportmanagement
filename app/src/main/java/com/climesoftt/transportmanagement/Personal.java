package com.climesoftt.transportmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.climesoftt.transportmanagement.utils.AccountManager;
import com.climesoftt.transportmanagement.utils.AlertDialogClass;
import com.climesoftt.transportmanagement.utils.Logout;
import com.climesoftt.transportmanagement.utils.WelcomeImage;

/**
 * Created by AtoZ on 3/27/2018.
 */

public class Personal extends AppCompatActivity{
    private AccountManager accountManager;
    private String USER_NAME = "";
    private String USER_IMAGE = "";
    private ImageView imageWelcome;
    private TextView tvName;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);

        tvName = findViewById(R.id.admin_title);
        imageWelcome = findViewById(R.id.user_profile_photo);

        //Display Login user Name
        accountManager = new AccountManager(this);
        USER_NAME = accountManager.getUserName();
        USER_IMAGE = accountManager.getUserImage();
        tvName.setText(USER_NAME);
        WelcomeImage.setWelcomeImage(this, USER_IMAGE, imageWelcome);
    }

    public void getPersonalRoutes(View view) {
        Intent intent = new Intent(this , AllRoutes.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void onMaintenanceClick(View view) {
        Intent intent = new Intent(this , AllMaintenceActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void onPersonalFaqClick(View view) {
        Intent intent = new Intent(this , DriverAndPersonalFaq.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
    public void onClickLogout(MenuItem item)
    {
        USER_NAME = "";
        USER_IMAGE = "";
        accountManager.deleteUserCredentials();
        this.finish();
        Logout.logoutUser(this);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_dashboard, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        // Logout Code...
        USER_NAME = "";
        USER_IMAGE = "";
        accountManager.deleteUserCredentials();
        AlertDialogClass.alertDialogue(this);
    }

    public void onPersonalMechanicClick(View view) {


    }
}
