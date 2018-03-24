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
    private String userName = "";
    private String userEmail = "";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_dashboard);
        tvName = findViewById(R.id.admin_title);

        //userEmail = getIntent().getStringExtra("USER_EMAIL");
        //Message.show(this, userEmail);
        //Display Login user Name
        //displayUserName();

    }
    public void displayUserName()
    {
        try
        {
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("Users");
            userRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for(DataSnapshot userSnapshot : dataSnapshot.getChildren())
                    {
                        User user = userSnapshot.getValue(User.class);
                        if(userEmail.equals(user.getEmail()))
                        {
                            tvName.setText(user.getName());
                        }
                    }

                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }catch (Exception e)
        {
            Message.show(this,"Something went wrong.\n"+e.getMessage());
        }
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

    public void addMaintenance(View view) {
        Intent intent = new Intent(this, AddMaintenenceActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
