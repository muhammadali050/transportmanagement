package com.climesoftt.transportmanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.climesoftt.transportmanagement.adapter.MechanicsAdaptor;
import com.climesoftt.transportmanagement.adapter.PersonAdapter;
import com.climesoftt.transportmanagement.utils.DeleteRecord;
import com.climesoftt.transportmanagement.utils.Message;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MechanicProfile extends AppCompatActivity {

    private TextView tv_mId , tv_mName, tv_mAddress, tv_mPhone;
    private String mcPId = "";
    private String pName = "";
    private String pAddress = "";
    private String pPhone = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mechanic_profile);

        tv_mId = findViewById(R.id.mPId);
        tv_mName = findViewById(R.id.user_profile_name);
        tv_mAddress = findViewById(R.id.user_profile_short_bio);
        tv_mPhone = findViewById(R.id.mPPhone);

        mcPId = MechanicsAdaptor.MECHANIC_ID;
        pName = MechanicsAdaptor.MECHANIC_NAME;
        pAddress = MechanicsAdaptor.MECHANIC_ADDRESS;
        pPhone = MechanicsAdaptor.MECHANIC_PHONE;

        tv_mId.setText(mcPId);
        tv_mName.setText(pName);
        tv_mAddress.setText(pAddress);
        tv_mPhone.setText(pPhone);

        try{
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        catch (Exception e){

        }
    }

    public void onClickEditMechanic(View view) {
        this.finish();
        Intent intent = new Intent(this, EditMechanic.class);
        intent.putExtra("MC_ID_KEY",mcPId);
        intent.putExtra("MC_NAME_KEY",pName);
        intent.putExtra("MC_ADDRESS_KEY",pAddress);
        intent.putExtra("MC_PHONE_KEY",pPhone);
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

    public void deleteMechanic(View view) {
        DeleteRecord.deleteRecordMethod(this , "mechanics" , mcPId);
        this.finish();
        Intent intent = new Intent(this, AllMechanicsActivity.class);
        startActivity(intent);
    }
}
