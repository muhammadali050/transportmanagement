package com.climesoftt.transportmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.climesoftt.transportmanagement.utils.DeleteRecord;

/**
 * Created by Ali on 3/19/2018.
 */

public class Route extends AppCompatActivity {

    private TextView tvTo, tvFrom , tvToolPlaza, tvPetrol, tvExtras, tvDescription;
    private String RouteID = "";
    private String RTo = "";
    private String RFrom = "";
    private String RTool = "";
    private String RPetrol = "";
    private String RExtras = "";
    private String RDescription = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);

        tvTo = findViewById(R.id.tv_destination);
        tvFrom = findViewById(R.id.tv_from);
        tvToolPlaza = findViewById(R.id.rTool);
        tvPetrol = findViewById(R.id.rPetrol);
        tvExtras = findViewById(R.id.rExtras);
        tvDescription = findViewById(R.id.tvRDescription);
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

        tvFrom.setText(RFrom);
        tvTo.setText(RTo);
        tvToolPlaza.setText(RTool);
        tvPetrol.setText(RPetrol);
        tvExtras.setText(RExtras);
        tvDescription.setText(RDescription);
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
        startActivity(intent);

    }

    public void routeDelete(View view) {

        DeleteRecord.deleteRecordMethod(this , "Routes" , RouteID);
        this.finish();
        Intent intent = new Intent(this, AllRoutes.class);
        this.startActivity(intent);
    }
}
