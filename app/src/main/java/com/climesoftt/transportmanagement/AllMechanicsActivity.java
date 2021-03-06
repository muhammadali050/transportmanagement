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

import com.climesoftt.transportmanagement.adapter.MechanicsAdaptor;
import com.climesoftt.transportmanagement.model.Person;
import com.climesoftt.transportmanagement.utils.AccountManager;
import com.climesoftt.transportmanagement.utils.MoveUserToDashboard;
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
    private ArrayList<Person> mechanicsList = new ArrayList<>();
    private MechanicsAdaptor mechanicsAdapter;
    private RecyclerView recyclerView;
    private DatabaseReference dref;
    private String USER_TYPE = "";
    private AccountManager accountManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_mechanics);

        accountManager = new AccountManager(this);
        USER_TYPE = accountManager.getUserAccountType();

        recyclerView = findViewById(R.id.rcvMechanics);
        mechanicsAdapter = new MechanicsAdaptor(this, mechanicsList);
        recyclerView.setAdapter(mechanicsAdapter) ;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fetchDataFromFirebase();

        try{
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        catch (Exception e){

        }
    }

    private void fetchDataFromFirebase()
    {
        dref = FirebaseDatabase.getInstance().getReference("mechanics");
        final PDialog pd = new PDialog(this).message("Loading. . .");
        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot mSnapshot : dataSnapshot.getChildren())
                {
                    Person pData = mSnapshot.getValue(Person.class);
                    mechanicsList.add(pData);
                }
                mechanicsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                pd.hide();
            }
        });
        pd.hide();

    }

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_add_user, menu);
        return true;
    }

    public void addUser(MenuItem item){
        this.finish();
        Intent intent = new Intent(this, AddMechanic.class);
        startActivity(intent);
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                this.finish();
                MoveUserToDashboard.moveUser(this,USER_TYPE);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        MoveUserToDashboard.moveUser(this,USER_TYPE);
    }
}
