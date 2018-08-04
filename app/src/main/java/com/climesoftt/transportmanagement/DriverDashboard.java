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
 * Created by Ali on 3/22/2018.
 */

public class DriverDashboard extends AppCompatActivity {
    private TextView tvName;
    private AccountManager accountManager;
    private String USER_NAME = "";
    private String USER_IMAGE = "";
    private ImageView imgViewDriverWelcome;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_dashboard);
        tvName = findViewById(R.id.admin_title);
        imgViewDriverWelcome = findViewById(R.id.user_photo);

        accountManager = new AccountManager(this);
        USER_NAME = accountManager.getUserName();
        USER_IMAGE = accountManager.getUserImage();
        //Display Login user Name
        //tvName.setText(USER_NAME);
        WelcomeImage.setWelcomeImage(this, USER_IMAGE, imgViewDriverWelcome);

    }

    public void onClickFaq(View view){
        Intent intent = new Intent(this , DriverAndPersonalFaq.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_dashboard, menu);
        return true;
    }

    public void onClickLogout(MenuItem item){
        // Logout Code...
        clearUserCredentials();
        accountManager.deleteUserCredentials();
        this.finish();
        Logout.logoutUser(DriverDashboard.this);
    }


    public void getDriverRoutes(View view) {
        Intent intent = new Intent(this, AllRoutes.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
    public void addMaintenance(View view) {
        Intent intent = new Intent(this, AddMaintenenceActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        // Logout Code...
        clearUserCredentials();
        accountManager.deleteUserCredentials();
        AlertDialogClass.alertDialogue(this);
    }

    public void clearUserCredentials()
    {
        USER_NAME = "";
        USER_IMAGE = "";
    }

    public void onClickMaintenance(View view) {
        Intent intent = new Intent(this, AllMaintenceActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
