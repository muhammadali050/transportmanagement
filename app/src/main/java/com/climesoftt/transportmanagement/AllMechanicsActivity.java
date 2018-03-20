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
import android.view.View;
import android.widget.Toast;

import com.climesoftt.transportmanagement.adapter.DriverAdapter;
import com.climesoftt.transportmanagement.adapter.MechanicsAdaptor;
import com.climesoftt.transportmanagement.adapter.PersonAdapter;
import com.climesoftt.transportmanagement.model.Person;
import com.climesoftt.transportmanagement.utils.FetchDataPerson;
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

public class AllMechanicsActivity extends AppCompatActivity {
    private ArrayList<Person> personsList = new ArrayList<>();
    private PersonAdapter personAdapter;
    private RecyclerView  rv;
    private DatabaseReference dref;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_all_mechanics);
        //Now use one PersonAdapter for Two Activities so here setContentView Change...Keep in mind for change
        setContentView(R.layout.activity_all_drivers);

        rv = findViewById(R.id.rcvUsers);
        personAdapter = new PersonAdapter(this, personsList);
        rv.setAdapter(personAdapter) ;
        rv.setLayoutManager(new LinearLayoutManager(this));

        /*
            For Reusability create function in FetchDataPerson class then use here.
         */
        new FetchDataPerson(this).fetchDataFromFirebase("mechanics",personsList,personAdapter);
        // Instead of this method create separate function in FetchDataPerson Class ..
        // fetchDataFromFirebase();

        try{
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        catch (Exception e){

        }
    }

    /*
    // Instead of this method create function in FetchDataPerson Class ..
    private void fetchDataFromFirebase()
    {
        dref = FirebaseDatabase.getInstance().getReference("mechanics");
        final PDialog pd = new PDialog(this).message("Loading. . .");
        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot driverSnapshot : dataSnapshot.getChildren())
                {
                    Person pData = driverSnapshot.getValue(Person.class);
                    personsList.add(pData);
                }
                personAdapter.notifyDataSetChanged();
                pd.hide();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                pd.hide();
            }
        });
    }
    */
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_add_user, menu);
        return true;
    }

    public void addUser(MenuItem item){
        Intent intent = new Intent(this, AddMechanic.class);
        startActivity(intent);
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
