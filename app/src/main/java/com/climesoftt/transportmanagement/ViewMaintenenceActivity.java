package com.climesoftt.transportmanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.climesoftt.transportmanagement.utils.DeleteRecord;

public class ViewMaintenenceActivity extends AppCompatActivity {

    private TextView tvStartDate, tvEndDate, tvDescription;

    private String mId, startDate, endDate, mDescription, mEmail, mUserType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_maintenence);

        tvStartDate = findViewById(R.id.tv_Vstart);
        tvEndDate = findViewById(R.id.tv_Vend);
        tvDescription = findViewById(R.id.tv_MDescription);

        Intent intent = getIntent();
        mId = intent.getStringExtra("MID");
        mDescription = intent.getStringExtra("DESCRIPTION");
        startDate = intent.getStringExtra("STARTDATE");
        endDate = intent.getStringExtra("ENDDATE");
        mEmail = intent.getStringExtra("USER_EMAIL");
        mUserType = intent.getStringExtra("USER_TYPE");

        tvStartDate.setText(startDate);
        tvEndDate.setText(endDate);
        tvDescription.setText(mDescription);

    }

    public void onClickEditMaintenence(View view) {
        // Go to edit activity
        Intent intent = new Intent(this, EditMaintenenceActivity.class);
        intent.putExtra("MID", mId);
        intent.putExtra("DESCRIPTION", mDescription);
        intent.putExtra("STARTDATE", startDate);
        intent.putExtra("ENDDATE", endDate);
        intent.putExtra("USER_EMAIL", mEmail);
        intent.putExtra("USER_TYPE", mUserType);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void onClickRouteDelete(View view) {
        // Delete code here
        DeleteRecord.deleteRecordMethod(this, "Maintenance", mId);
        Intent intent = new Intent(this, AllMaintenceActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
