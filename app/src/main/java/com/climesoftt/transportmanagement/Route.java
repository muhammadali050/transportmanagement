package com.climesoftt.transportmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.climesoftt.transportmanagement.model.Routes;
import com.climesoftt.transportmanagement.utils.AccountManager;
import com.climesoftt.transportmanagement.utils.DeleteRecord;
import com.climesoftt.transportmanagement.utils.Message;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Ali on 3/19/2018.
 */

public class Route extends AppCompatActivity {

    private TextView tvTo, tvFrom , tvToolPlaza, tvPetrol, tvExtras, tvDescription, tvDriverMail;
    private ImageView routeUpdate, routeDelete;
    private String RouteID = "";
    private String RTo = "";
    private String RFrom = "";
    private String RTool = "";
    private String RPetrol = "";
    private String RExtras = "";
    private String RDescription = "";
    private String RDriverEmail = "";
    private String TotalRoutes = "";
    private String USER_TYPE = "";
    private AccountManager accountManager;

    private DatabaseReference dbref;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);

        accountManager = new AccountManager(this);
        USER_TYPE = accountManager.getUserAccountType();

        routeUpdate = findViewById(R.id.rUpdateProfile);
        routeDelete = findViewById(R.id.rProfileDelete);

        if(USER_TYPE.equals("Driver"))
        {
            routeUpdate.setVisibility(View.GONE);
            routeDelete.setVisibility(View.GONE);
        }

        tvTo = findViewById(R.id.tv_destination);
        tvFrom = findViewById(R.id.tv_from);
        tvToolPlaza = findViewById(R.id.rTool);
        tvPetrol = findViewById(R.id.rPetrol);
        tvExtras = findViewById(R.id.rExtras);
        tvDescription = findViewById(R.id.tvRDescription);
        tvDriverMail = findViewById(R.id.tvShowDriver);

        //Call Function Below defined
        getDataOfCurrentSelectedRow();
        try{
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        catch (Exception e){

        }
    }

    private void getDataOfCurrentSelectedRow()
    {
        Intent intent = getIntent();
        RouteID = intent.getStringExtra("RID");
        RTo = intent.getStringExtra("RTO");
        RFrom = intent.getStringExtra("RFROM");
        RTool = intent.getStringExtra("TOOLPLAZA");
        RPetrol = intent.getStringExtra("PETROL");
        RExtras = intent.getStringExtra("EXTRACOST");
        RDescription = intent.getStringExtra("RDESCRIPTION");
        RDriverEmail = intent.getStringExtra("RDRIVER_EMAIL");

        tvFrom.setText(RFrom);
        tvTo.setText(RTo);
        tvToolPlaza.setText(RTool);
        tvPetrol.setText(RPetrol);
        tvExtras.setText(RExtras);
        tvDescription.setText(RDescription);
        tvDriverMail.setText(RDriverEmail);

        if(TextUtils.isEmpty(tvDriverMail.getText().toString()))
        {
            tvDriverMail.setText("NULL");
        }
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


    public void onClickEditRoute(View view) {
        this.finish();
        Intent intent = new Intent(this, EditRoute.class);
        intent.putExtra("RID",RouteID);
        intent.putExtra("RTO",RTo);
        intent.putExtra("RFROM",RFrom);
        intent.putExtra("RTOOL",RTool);
        intent.putExtra("RPETROL",RPetrol);
        intent.putExtra("REXTRAS",RExtras);
        intent.putExtra("RDESCRIPTION", RDescription);
        intent.putExtra("R_DRIVER_EMAIL", RDriverEmail);
        startActivity(intent);

    }

    public void routeDelete(View view) {

        DeleteRecord.deleteRecordMethod(this , "Routes" , RouteID);
        this.finish();
        Intent intent = new Intent(this, AllRoutes.class);
        this.startActivity(intent);
    }

    public void getDriverTotalRoutes(View view) {
        dbref = FirebaseDatabase.getInstance().getReference("Routes");
            dbref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    int routeCounter = 0;
                    for(DataSnapshot routesSnapshot : dataSnapshot.getChildren()) {
                        Routes data = routesSnapshot.getValue(Routes.class);
                        if(RDriverEmail.equals(data.getEmail()))
                        {
                            routeCounter++;
                        }
                    }
                    if(routeCounter!=0)
                    {
                        Toast.makeText(Route.this, "\nTotal No. of Routes : "+Integer.toString(routeCounter)+"\n",Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
    }
}
