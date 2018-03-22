package com.climesoftt.transportmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.climesoftt.transportmanagement.model.Person;
import com.climesoftt.transportmanagement.utils.Message;
import com.climesoftt.transportmanagement.utils.PDialog;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Ali on 3/19/2018.
 */

public class EditDriver extends AppCompatActivity {
    private TextView tv_dName, tv_dPhone, tv_dAddress;
    private String driverId, dName, dPhone, dAddress;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_driver);

        tv_dName = findViewById(R.id.dUName);
        tv_dPhone = findViewById(R.id.dUPhone);
        tv_dAddress = findViewById(R.id.dUAddress);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            driverId = bundle.getString("DR_ID_KEY");
            dName = bundle.getString("DR_NAME_KEY");
            dPhone = bundle.getString("DR_PHONE_KEY");
            dAddress = bundle.getString("DR_ADDRESS_KEY");
        }
        tv_dName.setText(dName);
        tv_dPhone.setText(dPhone);
        tv_dAddress.setText(dAddress);

        try{
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        catch (Exception e){

        }

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

    public void driverRecordUpdate(View view)
    {
     String uName = "";
     String uPhone = "";
     String uAddress = "";

     uName = tv_dName.getText().toString().trim();
     uPhone = tv_dPhone.getText().toString().trim();
     uAddress = tv_dAddress.getText().toString().trim();

        //Validation
        if(TextUtils.isEmpty(uName) || TextUtils.isEmpty(uPhone) || TextUtils.isEmpty(uAddress))
        {
            Message.show(this , "Please fill all the fields!");
            return;
        }

        final Person driver = new Person();
        driver.setId(driverId);
        driver.setName(uName);
        driver.setPhone(uPhone);
        driver.setAddress(uAddress);
        final PDialog pd = new PDialog(this).message("Updating record. . .");
        try
        {
            //String uniqueId = String.valueOf(new Date().getTime());
            DatabaseReference driverRef = FirebaseDatabase.getInstance().getReference().child("drivers").child(driverId);
            driverRef.setValue(driver);
            Message.show(this,"Record updated successfully.");
            driverId = "";

            this.finish();
            Intent int_newActivity = new Intent(this, AllDriversActivity.class);
            startActivity(int_newActivity);

        }catch (Exception e)
        {
            pd.hide();
            Message.show(this,"Something went wrong.\n"+e.getMessage());
        }
        pd.hide();
    }
}
