package com.climesoftt.transportmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.climesoftt.transportmanagement.adapter.DriverAdapter;
import com.climesoftt.transportmanagement.adapter.PersonAdapter;
import com.climesoftt.transportmanagement.utils.DeleteRecord;
import com.climesoftt.transportmanagement.utils.Message;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Ali on 3/18/2018.
 */

public class DriverProfile extends AppCompatActivity{
    private TextView tvId, tvName, tvAddress, tvPhone;
    private String dPId = "";
    private String pName = "";
    private String pAddress = "";
    private String pPhone = "";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_profile);

        tvId = findViewById(R.id.pId);
        tvName = findViewById(R.id.user_profile_name);
        tvAddress = findViewById(R.id.user_profile_short_bio);
        tvPhone = findViewById(R.id.pPhone);

        //Receive data from PersonAdapter.java Class
        dPId = DriverAdapter.DRIVER_ID;
        pName = DriverAdapter.DRIVER_NAME;
        pAddress = DriverAdapter.DRIVER_ADDRESS;
        pPhone = DriverAdapter.DRIVER_PHONE;

        tvId.setText(dPId);
        tvName.setText(pName);
        tvAddress.setText(pAddress);
        tvPhone.setText(pPhone);

        try{
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        catch (Exception e){

        }
    }

    public void onClickEditDriver(View view) {
        this.finish();
        Intent intent = new Intent(this, EditDriver.class);
        intent.putExtra("DR_ID_KEY",dPId);
        intent.putExtra("DR_NAME_KEY",pName);
        intent.putExtra("DR_ADDRESS_KEY",pAddress);
        intent.putExtra("DR_PHONE_KEY",pPhone);
        startActivity(intent);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void deleteDriverData(View view) {
        DeleteRecord.deleteRecordMethod(this , "drivers" , dPId);
        this.finish();
        Intent intent = new Intent(this, AllDriversActivity.class);
        startActivity(intent);

    }
}
