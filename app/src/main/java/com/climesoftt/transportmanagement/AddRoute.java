package com.climesoftt.transportmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.climesoftt.transportmanagement.model.*;
import com.climesoftt.transportmanagement.utils.DriversList;
import com.climesoftt.transportmanagement.utils.Message;
import com.climesoftt.transportmanagement.utils.PDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Ali on 3/19/2018.
 */

public class AddRoute extends AppCompatActivity{
    private DatabaseReference dbRef;
    private EditText toCity, fromCity, toolPlaza, petrolCost, extraCost;
    private Spinner spinnerDriver;
    private ArrayAdapter adapterSpinner;
    private ArrayList<String> arrayList = new ArrayList<>();
    private String getSelectedDriver = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_route);

        dbRef = FirebaseDatabase.getInstance().getReference("Routes");

        toCity = findViewById(R.id.rToCity);
        fromCity = findViewById(R.id.rFromCity);
        toolPlaza  = findViewById(R.id.rToolPlaza);
        petrolCost   = findViewById(R.id.rPetrol);
        extraCost    = findViewById(R.id.rExtraCost);
        spinnerDriver = findViewById(R.id.rDriverSpinner);

        //Method for showing driver names in spinner
        getDriversList();
        adapterSpinner = new ArrayAdapter(this,android.R.layout.simple_spinner_item, arrayList);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDriver.setAdapter(adapterSpinner);

       spinnerDriver.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String driver =  parent.getItemAtPosition(position).toString();
                //String item = arrayList.get(position);
                Message.show(AddRoute.this, driver);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Message.show(AddRoute.this, "Nothing Selected");
            }
        });


        try{
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        catch (Exception e){

        }
    }

    public void addRoute(View view)
    {
        //String dr = spinnerDriver.getSelectedItem().toString();
        //Log.d("DR",dr);
        Message.show(AddRoute.this, arrayList.toString());


        int getId = GenerateRandomNumber.randomNum();
        String rId = Integer.toString(getId);
        String to = toCity.getText().toString();
        String from = fromCity.getText().toString();
        String tool = toolPlaza.getText().toString();
        String petrol = petrolCost.getText().toString();
        String extras = extraCost.getText().toString();
        //String driver = spinnerDriver.getSelectedItem().toString();
        //Message.show(AddRoute.this, driver);
        if(TextUtils.isEmpty(to) || TextUtils.isEmpty(from) || TextUtils.isEmpty(tool) || TextUtils.isEmpty(petrol) || TextUtils.isEmpty(extras))
        {
            Message.show(AddRoute.this, "Please fill all fields!");
            return;
        }

        final PDialog pd = new PDialog(this).message("Route adding. . . ");
        try
        {
           // String uniqueId = String.valueOf(new Date().getTime());
            Routes route = new Routes();
            route.setId(rId);
            route.setToCity(to);
            route.setFromCity(from);
            route.setTooPlaza(tool);
            route.setPetrolCost(petrol);
            route.setExtras(extras);
            //route.setDriver(driver);

            dbRef.child(rId).setValue(route);
            Message.show(AddRoute.this,"Added successfully.");

            this.finish();
            Intent intent = new Intent(this, AllRoutes.class);
            startActivity(intent);


        }catch (Exception e)
        {
            pd.hide();
            Message.show(AddRoute.this,"Something went wrong.\n"+e.getMessage());
        }
        pd.hide();
        

    }

    public void getDriversList()
    {
        //Call Static Fucntion and get drivers list
        //DriversList.getDriversList(arrayList);
       DatabaseReference driverRef = FirebaseDatabase.getInstance().getReference("drivers");
        driverRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot driverSnapshot : dataSnapshot.getChildren())
                {
                    Person data = driverSnapshot.getValue(Person.class);
                    getSelectedDriver = data.getName();
                    arrayList.add(getSelectedDriver);
                    Log.d("KEY",getSelectedDriver);
                    //Message.show(AddRoute.this, arrayList.toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        //Log.d("KEY",arrayList.toString());
      /*  adapterSpinner = new ArrayAdapter(this, android.R.layout.simple_spinner_item, arrayList);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDriver.setAdapter(adapterSpinner); */
        //adapterSpinner.notifyDataSetChanged();
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
