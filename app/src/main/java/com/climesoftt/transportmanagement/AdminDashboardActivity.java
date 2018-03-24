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

import com.climesoftt.transportmanagement.model.Routes;
import com.climesoftt.transportmanagement.model.User;
import com.climesoftt.transportmanagement.utils.Message;
import com.climesoftt.transportmanagement.utils.PDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Ali on 3/14/2018.
 */

public class AdminDashboardActivity extends AppCompatActivity {
    private TextView tvName;
    private String userName = "";
    private String userEmail = "";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);
        tvName = findViewById(R.id.admin_title);
        tvName.setText("");
        userEmail = getIntent().getStringExtra("USER_EMAIL");
        getUserName();
        tvName.setText(userName);


        try{
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        catch (Exception e){

        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_dashboard, menu);
        return true;
    }

    public void onClickLogout(MenuItem item){
        // Logout Code...
    }


    public void getUserName()
    {
        DatabaseReference uRef = FirebaseDatabase.getInstance().getReference("Users");
        uRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot userSnapshot : dataSnapshot.getChildren())
                {
                    User data = userSnapshot.getValue(User.class);
                    if(data.getEmail().equals(userEmail))
                    {
                        userName = data.getName();
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void onClickMechanic(View view) {
        Intent intent = new Intent(this, AllMechanicsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void onClickUsers(View view) {
        Intent intent = new Intent(this, AllDriversActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void onClickRoutes(View view) {
        Intent intent = new Intent(this, AllRoutes.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
    public void driverDashboard(View view) {
        Intent intent = new Intent(this, DriverDashboard.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                //alertDialogue();
                return false;
        }
        return super.onOptionsItemSelected(item);
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
                        AdminDashboardActivity.super.onBackPressed();
                        System.exit(0);
                    }
                }).create().show();
    }





}
