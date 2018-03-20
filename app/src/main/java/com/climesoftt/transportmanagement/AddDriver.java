package com.climesoftt.transportmanagement;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.climesoftt.transportmanagement.model.Person;
import com.climesoftt.transportmanagement.utils.Message;
import com.climesoftt.transportmanagement.utils.PDialog;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

/**
 * Created by Ali on 3/19/2018.
 */

public class AddDriver extends AppCompatActivity {

    private DatabaseReference dbRef;
    private EditText dName,dPhone,dAddress;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_driver);
        dbRef = FirebaseDatabase.getInstance().getReference();

        dName = findViewById(R.id.etDName);
        dPhone = findViewById(R.id.etDPhone);
        dAddress = findViewById(R.id.rExtras);


        try{
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        catch (Exception e){

        }
    }

    public void addDriver(View view)
    {
        String name = dName.getText().toString();
        String phone = dPhone.getText().toString();
        String address = dAddress.getText().toString();
        //Validation
        if(TextUtils.isEmpty(name) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(address))
        {
            Message.show(AddDriver.this , "Please fill all the fields.");
            return;
        }

        final Person driver = new Person();
        driver.setName(name);
        driver.setPhone(phone);
        driver.setAddress(address);
        final PDialog pd = new PDialog(this).message("Person Registration.");
        try
        {
            String uniqueId = String.valueOf(new Date().getTime());
            DatabaseReference driverRef = dbRef.child("drivers").child(uniqueId);
            driverRef.setValue(driver);
            Message.show(AddDriver.this,"Registered successfully.");

        }catch (Exception e)
        {
            pd.hide();
            Message.show(AddDriver.this,"Something went wrong.\n"+e.getMessage());
        }
        pd.hide();
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


}
