package com.climesoftt.transportmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.climesoftt.transportmanagement.adapter.DriverAdapter;
import com.climesoftt.transportmanagement.adapter.PersonAdapter;
import com.climesoftt.transportmanagement.model.Person;
import com.climesoftt.transportmanagement.utils.FetchDataPerson;
import com.climesoftt.transportmanagement.utils.Message;
import com.climesoftt.transportmanagement.utils.PDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Ali on 3/14/2018.
 */

public class AllDriversActivity extends AppCompatActivity {

    //This code is for DriverAdapter
    private ArrayList<Person> driversList = new ArrayList<>();
    public static DriverAdapter driverAdapter;
    private RecyclerView  rv;
    private DatabaseReference dref;


    /*  //For Generic
    private ArrayList<Person> personsList = new ArrayList<>();
    private PersonAdapter personAdapter;
    private RecyclerView  rv;
    private DatabaseReference dref;  */

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_drivers);

        rv = (RecyclerView)findViewById(R.id.rcvUsers);
        driverAdapter= new DriverAdapter(this, driversList);
        rv.setAdapter(driverAdapter) ;
        rv.setLayoutManager(new LinearLayoutManager(this));
        fetchDataFromFirebase();
        /*
            For Reusability create function in FetchDataPerson class then use here.

        new FetchDataPerson(this).fetchDataFromFirebase("drivers",driversList,driverAdapter);
        driversList.clear();
        */

        try{
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        catch (Exception e){

        }
    }


    // Instead of this method create function in FetchDataPerson Class ..
    private void fetchDataFromFirebase()
    {
        dref = FirebaseDatabase.getInstance().getReference("drivers");
        final PDialog pd = new PDialog(this).message("Loading. . .");
        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot driverSnapshot : dataSnapshot.getChildren())
                {
                    Person pData = driverSnapshot.getValue(Person.class);
                    driversList.add(pData);
                }
                driverAdapter.notifyDataSetChanged();
                pd.hide();
               // REFERENCE_CHILD_NAME = dref.getKey();
               // Message.show(AllDriversActivity.this , REFERENCE_CHILD_NAME);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                pd.hide();
            }
        });
    }


    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_add_user, menu);
        return true;
    }

    public void addUser(MenuItem item){
        //this.finish();
        Intent intent = new Intent(this, AddDriver.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                driversList.clear();
                rv.notifyAll();
                this.finish();
                //Intent intent = new Intent(this, AdminDashboardActivity.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
