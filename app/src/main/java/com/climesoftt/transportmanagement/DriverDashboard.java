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

import com.climesoftt.transportmanagement.model.User;
import com.climesoftt.transportmanagement.utils.DisplayUserName;
import com.climesoftt.transportmanagement.utils.Logout;
import com.climesoftt.transportmanagement.utils.Message;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Ali on 3/22/2018.
 */

public class DriverDashboard extends AppCompatActivity {
    private TextView tvName;
    public static String USERNAME = "";
    public static String USER_TYPE = "";
    public static String USER_EMAIL = "";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_dashboard);
        tvName = findViewById(R.id.admin_title);

        Intent intent = getIntent();
        USERNAME = intent.getStringExtra("USER_NAME");
        USER_TYPE = intent.getStringExtra("USER_TYPE");
        USER_EMAIL = intent.getStringExtra("USER_EMAIL");
        //Display Login user Name
        tvName.setText(USERNAME);

    }

    public void onClickFaq(View view){
        Intent intent = new Intent(this , DriverFaq.class);
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
        this.finish();
        Logout.logoutUser(DriverDashboard.this);
    }


    public void getDriverRoutes(View view) {
        Intent int_newActivity = new Intent(this, AllRoutes.class);
        int_newActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(int_newActivity);
    }
    public void addMaintenance(View view) {
        Intent intent = new Intent(this, AllMaintenceActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
