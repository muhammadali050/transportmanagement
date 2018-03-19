package com.climesoftt.transportmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Ali on 3/14/2018.
 */

public class AdminDashboardActivity extends AppCompatActivity {
    private TextView tvName;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);
        tvName = findViewById(R.id.admin_title);
        String uname = getIntent().getStringExtra("userName");
        tvName.setText(uname);

        try{
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        catch (Exception e){

        }
    }

    public void onClickMechanic(View view) {
        Intent intent = new Intent(this, AllMechanicsActivity.class);
        startActivity(intent);
    }

    public void onClickUsers(View view) {
        Intent intent = new Intent(this, AllDriversActivity.class);
        startActivity(intent);
    }

    public void onClickRoutes(View view) {
        Intent intent = new Intent(this, AllRoutes.class);
        startActivity(intent);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                this.finish();;
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
