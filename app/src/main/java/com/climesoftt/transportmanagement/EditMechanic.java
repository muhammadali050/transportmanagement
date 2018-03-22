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

public class EditMechanic extends AppCompatActivity {
    private TextView tv_mName, tv_mPhone, tv_mAddress;
    private String mId, mName, mPhone, mAddress;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_mechanic);

        tv_mName = findViewById(R.id.mUName);
        tv_mPhone = findViewById(R.id.mUPhone);
        tv_mAddress = findViewById(R.id.mUAddress);

        Intent intent = getIntent();
        if(intent!= null)
        {
            mId = intent.getStringExtra("MC_ID_KEY");
            mName = intent.getStringExtra("MC_NAME_KEY");
            mPhone = intent.getStringExtra("MC_PHONE_KEY");
            mAddress = intent.getStringExtra("MC_ADDRESS_KEY");
        }
        tv_mName.setText(mName);
        tv_mPhone.setText(mPhone);
        tv_mAddress.setText(mAddress);

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

    public void updateMechanicRecord(View view) {
        String uName = "";
        String uPhone = "";
        String uAddress = "";

        uName = tv_mName.getText().toString().trim();
        uPhone = tv_mPhone.getText().toString().trim();
        uAddress = tv_mAddress.getText().toString().trim();

        //Validation
        if(TextUtils.isEmpty(uName) || TextUtils.isEmpty(uPhone) || TextUtils.isEmpty(uAddress))
        {
            Message.show(this , "Please fill all the fields!");
            return;
        }

        final Person mechanics = new Person();
        mechanics.setId(mId);
        mechanics.setName(uName);
        mechanics.setPhone(uPhone);
        mechanics.setAddress(uAddress);
        final PDialog pd = new PDialog(this).message("Updating record. . .");
        try
        {
            //String uniqueId = String.valueOf(new Date().getTime());
            DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("mechanics").child(mId);
            dbRef.setValue(mechanics);
            Message.show(this,"Record updated successfully.");
            this.finish();
            Intent int_newActivity = new Intent(this, AllMechanicsActivity.class);
            startActivity(int_newActivity);

        }catch (Exception e)
        {
            pd.hide();
            Message.show(this,"Something went wrong.\n"+e.getMessage());
        }
        pd.hide();
    }
}
