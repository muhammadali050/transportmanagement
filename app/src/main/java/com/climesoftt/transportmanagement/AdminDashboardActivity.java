package com.climesoftt.transportmanagement;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.climesoftt.transportmanagement.utils.AccountManager;
import com.climesoftt.transportmanagement.utils.AlertDialogClass;
import com.climesoftt.transportmanagement.utils.Logout;

/**
 * Created by Ali on 3/14/2018.
 */

public class AdminDashboardActivity extends AppCompatActivity {
    private TextView tvName;
    private String USER_NAME = "";
    private AccountManager accountManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);
        tvName = findViewById(R.id.admin_title);

        accountManager = new AccountManager(this);
        USER_NAME = accountManager.getUserName();
        //Display Login user Name
        tvName.setText(USER_NAME);

        try{
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().hide();
        }
        catch (Exception e){

        }
    }

    public void onClickMechanic(View view) {
        Intent intent = new Intent(this, AllMechanicsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void onClickLogout(MenuItem item)
    {
        USER_NAME = "";
        accountManager.deleteUserCredentials();
        this.finish();
        Logout.logoutUser(AdminDashboardActivity.this);

    }

    public void onClickUsers(View view) {
        this.finish();
        Intent intent = new Intent(this, AllDriversActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void onClickRoutes(View view) {
        this.finish();
        Intent intent = new Intent(this, AllRoutes.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
    public void maintenance(View view) {
        this.finish();
        Intent intent = new Intent(this, AllMaintenceActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void onClickFaq(View view) {
        this.finish();
        Intent intent = new Intent(this, DriverAndPersonalFaq.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_dashboard, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                return false;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        AlertDialogClass.alertDialogue(this);
    }



}
