package com.climesoftt.transportmanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.climesoftt.transportmanagement.adapter.MechanicsAdaptor;
import com.climesoftt.transportmanagement.utils.DeleteRecord;
import com.squareup.picasso.Picasso;

public class MechanicProfile extends AppCompatActivity {

    private TextView tv_mId , tv_mName, tv_mAddress, tv_mPhone;
    private ImageView imgProfile;
    private String mcPId = "";
    private String pName = "";
    private String pAddress = "";
    private String pPhone = "";
    private String pImage = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mechanic_profile);

        tv_mId = findViewById(R.id.mPId);
        tv_mName = findViewById(R.id.user_profile_name);
        tv_mAddress = findViewById(R.id.user_profile_short_bio);
        tv_mPhone = findViewById(R.id.mPPhone);
        imgProfile = findViewById(R.id.header_cover_image);

        mcPId = MechanicsAdaptor.MECHANIC_ID;
        pName = MechanicsAdaptor.MECHANIC_NAME;
        pAddress = MechanicsAdaptor.MECHANIC_ADDRESS;
        pPhone = MechanicsAdaptor.MECHANIC_PHONE;
        pImage = MechanicsAdaptor.MECHANIC_IMAGE;

        tv_mId.setText(mcPId);
        tv_mName.setText(pName);
        tv_mAddress.setText(pAddress);
        tv_mPhone.setText(pPhone);
        if(pImage != null && !TextUtils.isEmpty(pImage))
        {
            Picasso.with(this).load(pImage).placeholder(R.drawable.profile_headwer_photo).fit().centerCrop().into(imgProfile);
        }else
        {
            imgProfile.setImageResource(R.drawable.profile_headwer_photo);
        }

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
        intent.putExtra("MC_IMAGE_KEY",pImage);
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
        DeleteRecord.deleteRecordMethod(this , "Users" , mcPId);
        this.finish();
        Intent intent = new Intent(this, AllMechanicsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
