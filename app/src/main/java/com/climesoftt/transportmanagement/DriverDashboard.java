package com.climesoftt.transportmanagement;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Ali on 3/22/2018.
 */

public class DriverDashboard extends AppCompatActivity {
    private TextView tvName;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_dashboard);
        tvName = findViewById(R.id.admin_title);
        String uname = getIntent().getStringExtra("USERNAME");
        tvName.setText(uname);
    }

    public void onClickFaq(View view){
        Intent intent = new Intent(this , DriverFaq.class);
        startActivity(intent);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_dashboard, menu);
        return true;
    }

    public void onClickLogout(MenuItem item){
        // Logout Code...
    }


    public void getDriverRoutes(View view) {
        Intent int_newActivity = new Intent(this, AllRoutes.class);
        startActivity(int_newActivity);
    }
    @Override
    public void onBackPressed() {
        alertDialogue();
    }

    public void alertDialogue()
    {
        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to logout?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        DriverDashboard.super.onBackPressed();
                        //finish();
                        System.exit(0);
                    }
                }).create().show();
    }
}
