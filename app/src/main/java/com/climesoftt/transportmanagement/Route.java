package com.climesoftt.transportmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

/**
 * Created by Ali on 3/19/2018.
 */

public class Route extends AppCompatActivity {

    private TextView tvToolPlaza, tvPetrol, tvExtras;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);
        tvToolPlaza = findViewById(R.id.rTool);
        tvPetrol = findViewById(R.id.rPetrol);
        tvExtras = findViewById(R.id.rExtraCost);

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
        String tool = intent.getStringExtra("TOOLPLAZA");
        String petrol = intent.getStringExtra("PETROL");
        String extraCost = intent.getStringExtra("EXTRACOST");

        tvToolPlaza.setText(tool);
        tvPetrol.setText(petrol);
        tvExtras.setText(extraCost);
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
