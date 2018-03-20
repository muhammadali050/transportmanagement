package com.climesoftt.transportmanagement;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import com.climesoftt.transportmanagement.model.*;
import com.climesoftt.transportmanagement.utils.Message;
import com.climesoftt.transportmanagement.utils.PDialog;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

/**
 * Created by Ali on 3/19/2018.
 */

public class AddRoute extends AppCompatActivity {
    private DatabaseReference dbRef;
    private EditText toCity, fromCity, toolPlaza, petrolCost, extraCost;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_route);

        dbRef = FirebaseDatabase.getInstance().getReference("Routes");

        toCity = findViewById(R.id.rToCity);
        fromCity = findViewById(R.id.rFromCity);
        toolPlaza  = findViewById(R.id.rToolPlaza);
        petrolCost   = findViewById(R.id.rPetrol);
        extraCost    = findViewById(R.id.rExtras);


        try{
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        catch (Exception e){

        }
    }

    public void addRoute(View view)
    {
        //String to , from , tool, petrol , extras;
        String to = toCity.getText().toString();
        String from = fromCity.getText().toString();
        String tool = toolPlaza.getText().toString();
        String petrol = petrolCost.getText().toString();
        String extras = extraCost.getText().toString();

        if(TextUtils.isEmpty(to) || TextUtils.isEmpty(from) || TextUtils.isEmpty(tool) || TextUtils.isEmpty(petrol) || TextUtils.isEmpty(extras))
        {
            Message.show(AddRoute.this, "Please fill all fields!");
            return;
        }

        Routes route = new Routes();
        route.setToCity(to);
        route.setFromCity(from);
        route.setTooPlaza(tool);
        route.setPetrolCost(petrol);
        route.setExtras(extras);

        final PDialog pd = new PDialog(this).message("Route adding. . . ").show();
        try
        {
            String uniqueId = String.valueOf(new Date().getTime());
            DatabaseReference routeRef = dbRef.child("drivers").child(uniqueId);
            routeRef.setValue(route);
            Message.show(AddRoute.this,"Added successfully.");

        }catch (Exception e)
        {
            pd.hide();
            Message.show(AddRoute.this,"Something went wrong.\n"+e.getMessage());
        }
        pd.hide();
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
